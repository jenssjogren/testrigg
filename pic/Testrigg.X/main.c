/*
 * File:   main.c
 * Author: Jens
 *
 * Created on den 29 mars 2018, 15:27
 */

// PIC16F15344 Configuration Bit Settings

// 'C' source line config statements

// CONFIG1
#pragma config FEXTOSC = OFF    // External Oscillator mode selection bits (Oscillator not enabled)
#pragma config RSTOSC = HFINT32 // Power-up default value for COSC bits (HFINTOSC with OSCFRQ= 32 MHz and CDIV = 1:1)
#pragma config CLKOUTEN = OFF   // Clock Out Enable bit (CLKOUT function is disabled; i/o or oscillator function on OSC2)
#pragma config CSWEN = ON       // Clock Switch Enable bit (Writing to NOSC and NDIV is allowed)
#pragma config FCMEN = ON       // Fail-Safe Clock Monitor Enable bit (FSCM timer enabled)

// CONFIG2
#pragma config MCLRE = ON       // Master Clear Enable bit (MCLR pin is Master Clear function)
#pragma config PWRTE = OFF      // Power-up Timer Enable bit (PWRT disabled)
#pragma config LPBOREN = OFF    // Low-Power BOR enable bit (ULPBOR disabled)
#pragma config BOREN = ON       // Brown-out reset enable bits (Brown-out Reset Enabled, SBOREN bit is ignored)
#pragma config BORV = LO        // Brown-out Reset Voltage Selection (Brown-out Reset Voltage (VBOR) set to 1.9V on LF, and 2.45V on F Devices)
#pragma config ZCD = OFF        // Zero-cross detect disable (Zero-cross detect circuit is disabled at POR.)
#pragma config PPS1WAY = ON     // Peripheral Pin Select one-way control (The PPSLOCK bit can be cleared and set only once in software)
#pragma config STVREN = ON      // Stack Overflow/Underflow Reset Enable bit (Stack Overflow or Underflow will cause a reset)

// CONFIG3
#pragma config WDTCPS = WDTCPS_31// WDT Period Select bits (Divider ratio 1:65536; software control of WDTPS)
#pragma config WDTE = OFF       // WDT operating mode (WDT Disabled, SWDTEN is ignored)
#pragma config WDTCWS = WDTCWS_7// WDT Window Select bits (window always open (100%); software control; keyed access not required)
#pragma config WDTCCS = SC      // WDT input clock selector (Software Control)

// CONFIG4
#pragma config BBSIZE = BB512   // Boot Block Size Selection bits (512 words boot block size)
#pragma config BBEN = OFF       // Boot Block Enable bit (Boot Block disabled)
#pragma config SAFEN = OFF      // SAF Enable bit (SAF disabled)
#pragma config WRTAPP = OFF     // Application Block Write Protection bit (Application Block not write protected)
#pragma config WRTB = OFF       // Boot Block Write Protection bit (Boot Block not write protected)
#pragma config WRTC = OFF       // Configuration Register Write Protection bit (Configuration Register not write protected)
#pragma config WRTSAF = OFF     // Storage Area Flash Write Protection bit (SAF not write protected)
#pragma config LVP = ON         // Low Voltage Programming Enable bit (Low Voltage programming enabled. MCLR/Vpp pin function is MCLR.)

// CONFIG5
#pragma config CP = OFF         // UserNVM Program memory code protection bit (UserNVM code protection disabled)

// #pragma config statements should precede project file includes.
// Use project enums instead of #define for ON and OFF.

#include <xc.h>
#include <string.h>
#include <stdio.h>
#define _XTAL_FREQ 32000000

int timerSetpoint = 0;
int timerActual = 0;
char timerDone = 0;

enum peripheral {
    gps, wifi, sigfox, gsm, bt
};

void initBTSerial();
void setActivePeripheral(enum peripheral peri);

void serialWritePeripheral(char c);
void serialPrintPeripheral(char s[]);
char serialReadPeripheral();
char serialFindPeripheral(char s[]);

void serialWriteBT(char c);
void serialPrintBT(char s[]);
char serialReadBT();
char serialFindBT(char s[]);

void getWiFiData();
void getGSMData();
void getGPSData();
void getSigfoxData();
void sendSigfox(char message[]);

void waitForCommand();

void interrupt timerCount();
void startTimer(int seconds);

void main(void) {
    //Disable analog inputs for serial connections
    ANSELA = 0;
    ANSELB = 0;
    ANSELC = 0;

    //RB7 trigger pin for energy measurement
    TRISB7 = 0;
    LATB7 = 0;

    //Enable interrupt
    INTCONbits.GIE = 1;
    INTCONbits.PEIE = 1;

    //Initialize serial connections
    __delay_ms(5000);
    initBTSerial();
    setActivePeripheral(sigfox);

    while (1) {
        waitForCommand();

    }
    return;
}

void initBTSerial() {
    //BLUETOOTH:    RB5 RX, RB6 TX
    //Using EUSART2

    //TX
    RB6PPS = 0x11; //TX2 on RB6
    TX2STAbits.BRGH = 1; //High speed
    SP2BRG = 16; //Baud 115200
    TX2STAbits.SYNC = 0; //Asynchronous
    RC2STAbits.SPEN = 1; //Enable serial
    TX2STAbits.TXEN = 1; //Enable transmission

    //RX
    TRISB5 = 1;
    RX2DTPPSbits.RX2DTPPS = 0x0D; //RX on RB5
    RC2STAbits.CREN = 1; //Enable reception
}

void serialWritePeripheral(char c) {
    while (!PIR3bits.TX1IF) {
    }
    TX1REG = c;
}

void serialPrintPeripheral(char s[]) {
    int i = 0;
    while (s[i] != '\0') {
        serialWritePeripheral(s[i]);
        i++;
    }
}

char serialReadPeripheral() {
    while (!PIR3bits.RC1IF) { //character received
    }
    if (1 == RC1STAbits.OERR) { //EUSART1 error - restart
        RC1STAbits.SPEN = 0;
        RC1STAbits.SPEN = 1;
    }
    return RC1REG; //return received char
}

char serialFindPeripheral(char s[]) {
    int i = 0;
    while (s[i] != '\0') {
        if (serialReadPeripheral() == s[i]) {
            i++;
        } else {
            return 0;
        }
    }
    return 1;
}

void setActivePeripheral(enum peripheral peri) {
    //Peripherals using EUSART1
    //WIFI:         RA5 RX, RA4 TX  115200
    //GPS:          RC5 RX, RC4 TX  9600
    //GSM:          RC0 RX, RC1 TX  115200
    //SIGFOX:       RC3 RX, RC6 TX  9600
    //BLUETOOTH:    RB5 RX, RB6 TX  115200

    //Reset tx pin configuration
    RC6PPS = 0x00;
    RC1PPS = 0x00;
    RA4PPS = 0x00;
    RC4PPS = 0x00;


    switch (peri) {
        case sigfox:
        {
            RC6PPS = 0x0F; //TX1 on pin RC6
            SP1BRG = 207; //Baud 9600
            TRISC3 = 1;
            RX1DTPPSbits.RX1DTPPS = 0x13; //RX on RC3
            break;
        }
        case gsm:
        {
            RC1PPS = 0x0F; //TX1 on pin RC1
            SP1BRG = 16; //Baud 115200
            TRISC0 = 1;
            RX1DTPPSbits.RX1DTPPS = 0x10; //RX on RC0
            break;
        }
        case wifi:
        {
            RA4PPS = 0x0F; //TX1 on pin RA4
            SP1BRG = 16; //Baud 115200
            TRISA5 = 1;
            RX1DTPPSbits.RX1DTPPS = 0x05; //RX on RA5
            break;
        }
        case gps:
        {
            RC4PPS = 0x0F; //TX1 on pin RC4
            SP1BRG = 207; //Baud 9600
            TRISC5 = 1;
            RX1DTPPSbits.RX1DTPPS = 0x15; //RX on RC5
            break;
        }
    }

    TX1STAbits.BRGH = 1; //High speed
    TX1STAbits.SYNC = 0; //Asynchronous
    RC1STAbits.SPEN = 1; //Enable serial
    TX1STAbits.TXEN = 1; //Enable transmission
    RC1STAbits.CREN = 1; //Enable reception
}

void serialWriteBT(char c) {
    while (!PIR3bits.TX2IF) {
    }
    TX2REG = c;
}

void serialPrintBT(char s[]) {
    int i = 0;
    while (s[i] != '\0') {
        serialWriteBT(s[i]);
        i++;
    }
}

char serialReadBT() {
    while (!PIR3bits.RC2IF) { //character received
    }
    if (1 == RC2STAbits.OERR) { // EUSART2 error - restart
        RC2STAbits.SPEN = 0;
        RC2STAbits.SPEN = 1;
    }
    return RC2REG; //return received char
}

char serialFindBT(char s[]) {
    int i = 0;
    while (s[i] != '\0') {
        if (serialReadBT() == s[i]) {
            i++;
        } else {
            return 0;
        }
    }
    return 1;
}

void interrupt timerCount() {
    //Increasing timer count
    if (PIR0bits.TMR0IF) {
        timerActual++;
        PIR0bits.TMR0IF = 0;
    }
    if (timerActual == timerSetpoint) {
        timerDone = 1;
        T0CON0bits.T0EN = 0; //Disable T0
    }
}

void startTimer(int seconds) {
    //Timer0 initialize
    //prescaler 1:128 to toggle TMR0IF each second
    timerDone = 0;
    timerSetpoint = seconds;
    timerActual = 0;

    PIE0bits.TMR0IE = 1; //Enable Timer0 interrupt
    T0CON0 = 0b00010000; //16bit
    T0CON1 = 0b01000111; //fosc/4, sync, 1:128 prescaler
    T0CON0bits.T0EN = 1; //Enable T0    
}

void getWiFiData() {
    serialPrintBT("WIFI_START\r\n");
    setActivePeripheral(wifi);
    serialPrintPeripheral("AT+CWLAPOPT=1,12\r\n");
    __delay_ms(500);
    serialPrintPeripheral("AT+CWLAP\r\n");
    LATB7 = 1; //Trigger high
    __delay_ms(10);

    serialReadPeripheral(); //Clear buffer from char

    startTimer(3);
    while (!timerDone) {
        if (PIR3bits.RC1IF) {
            char c = serialReadPeripheral();
            if (c == 'O') { //Look for 'O' in "OK"
                LATB7 = 0; //Trigger low
            }
            serialWriteBT(c);
        }
    }
    serialPrintBT("WIFI_END\r\n");
    __delay_ms(10);
}

void getGSMData() {
    serialPrintBT("GSM_START\r\n");

    setActivePeripheral(gsm);
    serialPrintPeripheral("AT+CREG=2\r\n");
    __delay_ms(500);
    serialPrintPeripheral("AT+CSQ\r\n");
    __delay_ms(10);
    serialReadPeripheral(); //Clear buffer from char

    startTimer(1);
    while (!timerDone) {
        if (PIR3bits.RC1IF) {
            serialWriteBT(serialReadPeripheral());
        }
    }

    serialPrintPeripheral("AT+CREG?\r\n");
    __delay_ms(10);

    startTimer(1);
    while (!timerDone) {
        if (PIR3bits.RC1IF) {
            serialWriteBT(serialReadPeripheral());
        }
    }

    serialPrintBT("GSM_END\r\n");
    __delay_ms(10);
}

void getGPSData() {
    setActivePeripheral(gps);
    serialPrintBT("GPS_START\r\n");
    char found = 0;
    startTimer(3);
    while (!timerDone && !found) {
        if (PIR3bits.RC1IF) { //Check if GPS serial available            
            if (serialFindPeripheral("$GPGGA,")) {
                char c = 'c';
                while (c != '\n') {
                    c = serialReadPeripheral();
                    serialWriteBT(c);
                }
                found = 1;
            }
        }
    }
    serialPrintBT("GPS_END\r\n");
    __delay_ms(10);
}

void getSigfoxData() {
    setActivePeripheral(sigfox);
    serialPrintBT("SIGFOX_START\r\n");
    serialPrintPeripheral("AT\r\n");
    __delay_ms(1);

    serialReadPeripheral(); //Clear buffer from char

    startTimer(2);
    while (!timerDone) {
        if (PIR3bits.RC1IF) {
            serialWriteBT(serialReadPeripheral());
        }
    }

    serialPrintBT("SIGFOX_END\r\n");
    __delay_ms(10);
}

void sendSigfox(char hexMessage[]) {
    setActivePeripheral(sigfox);
    serialPrintPeripheral("AT$SF=");
    serialPrintPeripheral(hexMessage);
    serialPrintPeripheral("\r\n");
}

void sendSMS(char number[], char message[]) {
    //__delay_ms(300);
    setActivePeripheral(gsm);
    serialPrintPeripheral("AT+CMGF=1\r\n"); //Text mode
    __delay_ms(2000);
    serialPrintPeripheral("AT+CMGS=\"");
    serialPrintPeripheral(number);
    serialPrintPeripheral("\"\r\n");
    __delay_ms(3000);
    serialPrintPeripheral(message);
    __delay_ms(500);
    serialWritePeripheral('\x1A'); //CTRL+Z char
    __delay_ms(100);
    serialPrintPeripheral("\r\n");
    LATB7 = 1; //Trigger high
    __delay_ms(100);
}

void waitForCommand() {
    char command[32];

    while (!PIR3bits.RC2IF) { //Wait for incoming message
    }

    startTimer(2);
    int i = 0;
    while (!timerDone) {
        if (PIR3bits.RC2IF) {
            command[i] = serialReadBT();
            i++;
        }
    }
    command[i] = '\0';

    //Act upon command given
    if (strcmp(command, "GET_GPS\r\n") == 0) {
        getGPSData();
        serialPrintBT("MESSAGE_END\r\n");
    } else if (strcmp(command, "GET_GSM\r\n") == 0) {
        getGSMData();
        serialPrintBT("MESSAGE_END\r\n");
    } else if (strcmp(command, "GET_WIFI\r\n") == 0) {
        getWiFiData();
        serialPrintBT("MESSAGE_END\r\n");
    } else if (strcmp(command, "GET_ALL_SEND_SIGFOX\r\n") == 0) {
        sendSigfox("000000000000\r\n");
        getGPSData();
        getGSMData();
        getWiFiData();
        serialPrintBT("MESSAGE_END\r\n");
    } else if (strcmp(command, "GET_ALL\r\n") == 0) {
        getGPSData();
        getGSMData();
        getWiFiData();
        serialPrintBT("MESSAGE_END\r\n");
    } else if (strncmp(command, "SEND_SIGFOX:", 12) == 0) {
        char * p;
        p = command + 12;
        char message[30];
        int i = 0;

        while (*p != '\r') {
            message[i] = *p;
            p++;
            i++;
        }
        message[i] = '\0';

        sendSigfox(message);
    } else if ("SEND_SMS\r\n") {
        sendSMS("nummer", "Hello World");

        startTimer(4);
        while (!timerDone) {
            if (PIR3bits.RC1IF) {
                char c = serialReadPeripheral();
                if (c == 'O') { //Check for 'O' in "OK"
                    LATB7 = 0; //Trigger low
                }
                serialWriteBT(c);
            }
        }
    } else if ("SEND_SIGFOX") {
        sendSigfox("48656c6c6f20576f726c64"); //Hello World
        LATB7 = 1; //Trigger high
        startTimer(4);
        while (!timerDone) {
            if (PIR3bits.RC1IF) {
                char c = serialReadPeripheral();
                if (c == 'O') { //Check for 'O' in "OK"
                    LATB7 = 0; //Trigger low
                }
                serialWriteBT(c);
            }
        }
    }
}