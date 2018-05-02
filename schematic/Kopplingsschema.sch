<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE eagle SYSTEM "eagle.dtd">
<eagle version="8.6.0">
<drawing>
<settings>
<setting alwaysvectorfont="no"/>
<setting verticaltext="up"/>
</settings>
<grid distance="0.1" unitdist="inch" unit="inch" style="lines" multiple="1" display="no" altdistance="0.01" altunitdist="inch" altunit="inch"/>
<layers>
<layer number="1" name="Top" color="4" fill="1" visible="no" active="no"/>
<layer number="16" name="Bottom" color="1" fill="1" visible="no" active="no"/>
<layer number="17" name="Pads" color="2" fill="1" visible="no" active="no"/>
<layer number="18" name="Vias" color="2" fill="1" visible="no" active="no"/>
<layer number="19" name="Unrouted" color="6" fill="1" visible="no" active="no"/>
<layer number="20" name="Dimension" color="24" fill="1" visible="no" active="no"/>
<layer number="21" name="tPlace" color="7" fill="1" visible="no" active="no"/>
<layer number="22" name="bPlace" color="7" fill="1" visible="no" active="no"/>
<layer number="23" name="tOrigins" color="15" fill="1" visible="no" active="no"/>
<layer number="24" name="bOrigins" color="15" fill="1" visible="no" active="no"/>
<layer number="25" name="tNames" color="7" fill="1" visible="no" active="no"/>
<layer number="26" name="bNames" color="7" fill="1" visible="no" active="no"/>
<layer number="27" name="tValues" color="7" fill="1" visible="no" active="no"/>
<layer number="28" name="bValues" color="7" fill="1" visible="no" active="no"/>
<layer number="29" name="tStop" color="7" fill="3" visible="no" active="no"/>
<layer number="30" name="bStop" color="7" fill="6" visible="no" active="no"/>
<layer number="31" name="tCream" color="7" fill="4" visible="no" active="no"/>
<layer number="32" name="bCream" color="7" fill="5" visible="no" active="no"/>
<layer number="33" name="tFinish" color="6" fill="3" visible="no" active="no"/>
<layer number="34" name="bFinish" color="6" fill="6" visible="no" active="no"/>
<layer number="35" name="tGlue" color="7" fill="4" visible="no" active="no"/>
<layer number="36" name="bGlue" color="7" fill="5" visible="no" active="no"/>
<layer number="37" name="tTest" color="7" fill="1" visible="no" active="no"/>
<layer number="38" name="bTest" color="7" fill="1" visible="no" active="no"/>
<layer number="39" name="tKeepout" color="4" fill="11" visible="no" active="no"/>
<layer number="40" name="bKeepout" color="1" fill="11" visible="no" active="no"/>
<layer number="41" name="tRestrict" color="4" fill="10" visible="no" active="no"/>
<layer number="42" name="bRestrict" color="1" fill="10" visible="no" active="no"/>
<layer number="43" name="vRestrict" color="2" fill="10" visible="no" active="no"/>
<layer number="44" name="Drills" color="7" fill="1" visible="no" active="no"/>
<layer number="45" name="Holes" color="7" fill="1" visible="no" active="no"/>
<layer number="46" name="Milling" color="3" fill="1" visible="no" active="no"/>
<layer number="47" name="Measures" color="7" fill="1" visible="no" active="no"/>
<layer number="48" name="Document" color="7" fill="1" visible="no" active="no"/>
<layer number="49" name="Reference" color="7" fill="1" visible="no" active="no"/>
<layer number="51" name="tDocu" color="7" fill="1" visible="no" active="no"/>
<layer number="52" name="bDocu" color="7" fill="1" visible="no" active="no"/>
<layer number="88" name="SimResults" color="9" fill="1" visible="yes" active="yes"/>
<layer number="89" name="SimProbes" color="9" fill="1" visible="yes" active="yes"/>
<layer number="90" name="Modules" color="5" fill="1" visible="yes" active="yes"/>
<layer number="91" name="Nets" color="2" fill="1" visible="yes" active="yes"/>
<layer number="92" name="Busses" color="1" fill="1" visible="yes" active="yes"/>
<layer number="93" name="Pins" color="2" fill="1" visible="no" active="yes"/>
<layer number="94" name="Symbols" color="4" fill="1" visible="yes" active="yes"/>
<layer number="95" name="Names" color="7" fill="1" visible="yes" active="yes"/>
<layer number="96" name="Values" color="7" fill="1" visible="yes" active="yes"/>
<layer number="97" name="Info" color="7" fill="1" visible="yes" active="yes"/>
<layer number="98" name="Guide" color="6" fill="1" visible="yes" active="yes"/>
</layers>
<schematic xreflabel="%F%N/%S.%C%R" xrefpart="/%S.%C%R">
<libraries>
<library name="jens">
<packages>
<package name="ESP8266">
<pad name="UTXD" x="0" y="0" drill="0.7" shape="octagon"/>
<pad name="CH_PD" x="0" y="-2.54" drill="0.7" shape="octagon"/>
<pad name="RST" x="0" y="-5.08" drill="0.7" shape="octagon"/>
<pad name="VCC" x="0" y="-7.62" drill="0.7" shape="octagon"/>
<pad name="GND" x="5.08" y="0" drill="0.7" shape="square"/>
<pad name="URXD" x="5.08" y="-7.62" drill="0.7" shape="square"/>
<pad name="GPIO2" x="5.08" y="-2.54" drill="0.7" shape="octagon"/>
<pad name="GPIO0" x="5.08" y="-5.08" drill="0.7" shape="octagon"/>
<text x="0" y="2.54" size="1.27" layer="25">&gt;NAME</text>
<text x="0" y="-10.16" size="1.27" layer="27">&gt;VALUE</text>
</package>
<package name="A6_MINI_GSM-GPRS">
<pad name="MIC-" x="-10.16" y="-10.16" drill="0.7" shape="square"/>
<pad name="MIC+" x="-10.16" y="-7.62" drill="0.7"/>
<pad name="RST" x="-10.16" y="-5.08" drill="0.7"/>
<pad name="INT" x="-10.16" y="-2.54" drill="0.7"/>
<pad name="PWR" x="-10.16" y="0" drill="0.7"/>
<pad name="NETLED" x="-10.16" y="2.54" drill="0.7"/>
<pad name="GPIO14" x="-10.16" y="5.08" drill="0.7"/>
<pad name="VCC" x="-10.16" y="7.62" drill="0.7"/>
<pad name="HRX" x="12.7" y="5.08" drill="0.7"/>
<pad name="GND" x="12.7" y="2.54" drill="0.7"/>
<pad name="URX" x="12.7" y="0" drill="0.7"/>
<pad name="UTX" x="12.7" y="-2.54" drill="0.7"/>
<pad name="EAR_R" x="12.7" y="-5.08" drill="0.7"/>
<pad name="EAR_L" x="12.7" y="-7.62" drill="0.7"/>
<pad name="MIC2_P" x="12.7" y="-10.16" drill="0.7"/>
<pad name="HTX" x="12.7" y="7.62" drill="0.7" shape="square"/>
</package>
<package name="NEO-6M_GPS">
<pad name="TXD" x="0" y="-15" drill="0.7"/>
<pad name="GND" x="-2.54" y="-15" drill="0.7"/>
<pad name="VCC" x="-5.08" y="-15" drill="0.7"/>
<pad name="RXD" x="2.54" y="-15" drill="0.7"/>
<pad name="PPS" x="5.08" y="-15" drill="0.7" shape="square"/>
<wire x1="-12" y1="-17" x2="-12" y2="19" width="0.127" layer="21"/>
<wire x1="-12" y1="19" x2="12" y2="19" width="0.127" layer="21"/>
<wire x1="12" y1="19" x2="12" y2="-17" width="0.127" layer="21"/>
<wire x1="12" y1="-17" x2="-12" y2="-17" width="0.127" layer="21"/>
<text x="-11" y="17" size="1.27" layer="25">&gt;NAME</text>
<text x="-11" y="-11" size="1.27" layer="27">&gt;VALUE</text>
</package>
<package name="BRKWS01">
<pad name="GPIO8" x="-9.62" y="8.16" drill="0.7"/>
<pad name="GPIO7" x="-9.62" y="5.62" drill="0.7"/>
<pad name="GPIO6" x="-9.62" y="3.08" drill="0.7"/>
<pad name="GPIO5" x="-9.62" y="0.54" drill="0.7"/>
<pad name="GPIO4" x="-9.62" y="-2" drill="0.7"/>
<pad name="CPULED" x="-9.62" y="-4.54" drill="0.7"/>
<pad name="RADIOLED" x="-9.62" y="-7.08" drill="0.7"/>
<pad name="GPIO9" x="-9.62" y="-9.62" drill="0.7"/>
<pad name="TX" x="-7.08" y="-9.62" drill="0.7"/>
<pad name="RX" x="-4.54" y="-9.62" drill="0.7"/>
<pad name="LEDRX" x="-2" y="-9.62" drill="0.7"/>
<pad name="LEDTX" x="0.54" y="-9.62" drill="0.7"/>
<pad name="NC4/DBG_EN" x="3.08" y="-9.62" drill="0.7"/>
<pad name="/RST" x="5.62" y="-9.62" drill="0.7"/>
<pad name="VCC" x="8.16" y="-9.62" drill="0.7"/>
<pad name="GND" x="10.7" y="-9.62" drill="0.7"/>
<pad name="GPIO0" x="10.7" y="-4.54" drill="0.7"/>
<pad name="GPIO1" x="10.7" y="-2" drill="0.7"/>
<pad name="GPIO2" x="10.7" y="0.54" drill="0.7"/>
<pad name="GPIO3" x="10.7" y="3.08" drill="0.7"/>
<wire x1="-11" y1="-11" x2="-11" y2="10" width="0.127" layer="21"/>
<wire x1="-11" y1="10" x2="12" y2="10" width="0.127" layer="21"/>
<wire x1="12" y1="10" x2="12" y2="-11" width="0.127" layer="21"/>
<wire x1="12" y1="-11" x2="-11" y2="-11" width="0.127" layer="21"/>
<text x="-7" y="8" size="1.27" layer="25">&gt;NAME</text>
<text x="-7" y="6" size="1.27" layer="27">&gt;VALUE</text>
</package>
<package name="HC05_BLUETOOTH">
<pad name="STATE" x="7.62" y="0" drill="0.7" shape="square"/>
<pad name="RX" x="5.08" y="0" drill="0.7"/>
<pad name="TX" x="2.54" y="0" drill="0.7"/>
<pad name="GND" x="0" y="0" drill="0.7"/>
<pad name="VCC" x="-2.54" y="0" drill="0.7"/>
<pad name="EN" x="-5.08" y="0" drill="0.7"/>
<wire x1="-7.62" y1="2.54" x2="-7.62" y2="-2.54" width="0.127" layer="21"/>
<wire x1="-7.62" y1="-2.54" x2="10.16" y2="-2.54" width="0.127" layer="21"/>
<wire x1="10.16" y1="-2.54" x2="10.16" y2="2.54" width="0.127" layer="21"/>
<wire x1="10.16" y1="2.54" x2="-7.62" y2="2.54" width="0.127" layer="21"/>
<text x="-7" y="3" size="1.27" layer="25">&gt;NAME</text>
<text x="-7" y="-4" size="1.27" layer="27">&gt;VALUE</text>
</package>
</packages>
<symbols>
<symbol name="ESP8266">
<wire x1="-10.16" y1="10.16" x2="10.16" y2="10.16" width="0.254" layer="94"/>
<wire x1="10.16" y1="10.16" x2="10.16" y2="-10.16" width="0.254" layer="94"/>
<wire x1="10.16" y1="-10.16" x2="-10.16" y2="-10.16" width="0.254" layer="94"/>
<wire x1="-10.16" y1="-10.16" x2="-10.16" y2="10.16" width="0.254" layer="94"/>
<pin name="GND" x="15.24" y="7.62" length="middle" rot="R180"/>
<pin name="URXD" x="15.24" y="-7.62" length="middle" rot="R180"/>
<pin name="GPIO0" x="15.24" y="-2.54" length="middle" rot="R180"/>
<pin name="GPIO2" x="15.24" y="2.54" length="middle" rot="R180"/>
<pin name="UTXD" x="-15.24" y="7.62" length="middle"/>
<pin name="CH_PD" x="-15.24" y="2.54" length="middle"/>
<pin name="RST" x="-15.24" y="-2.54" length="middle"/>
<pin name="VCC" x="-15.24" y="-7.62" length="middle"/>
<text x="2.54" y="12.7" size="1.778" layer="95">&gt;NAME</text>
<text x="2.54" y="-12.7" size="1.778" layer="96">&gt;VALUE</text>
</symbol>
<symbol name="A6_MINI_GSM-GPRS">
<pin name="VCC" x="-17.78" y="17.78" length="middle"/>
<pin name="GPIO14" x="-17.78" y="12.7" length="middle"/>
<pin name="NETLED" x="-17.78" y="7.62" length="middle"/>
<pin name="PWR" x="-17.78" y="2.54" length="middle"/>
<pin name="INT" x="-17.78" y="-2.54" length="middle"/>
<pin name="RST" x="-17.78" y="-7.62" length="middle"/>
<pin name="MIC+" x="-17.78" y="-12.7" length="middle"/>
<pin name="MIC-" x="-17.78" y="-17.78" length="middle"/>
<pin name="HTX" x="17.78" y="17.78" length="middle" rot="R180"/>
<pin name="HRX" x="17.78" y="12.7" length="middle" rot="R180"/>
<pin name="GND" x="17.78" y="7.62" length="middle" rot="R180"/>
<pin name="URX" x="17.78" y="2.54" length="middle" rot="R180"/>
<pin name="UTX" x="17.78" y="-2.54" length="middle" rot="R180"/>
<pin name="EAR_R" x="17.78" y="-7.62" length="middle" rot="R180"/>
<pin name="EAR_L" x="17.78" y="-12.7" length="middle" rot="R180"/>
<pin name="MIC2_P" x="17.78" y="-17.78" length="middle" rot="R180"/>
<wire x1="-12.7" y1="20.32" x2="12.7" y2="20.32" width="0.254" layer="94"/>
<wire x1="12.7" y1="20.32" x2="12.7" y2="-20.32" width="0.254" layer="94"/>
<wire x1="12.7" y1="-20.32" x2="-12.7" y2="-20.32" width="0.254" layer="94"/>
<wire x1="-12.7" y1="-20.32" x2="-12.7" y2="20.32" width="0.254" layer="94"/>
<text x="5.08" y="22.86" size="1.778" layer="95">&gt;NAME</text>
<text x="5.08" y="-22.86" size="1.778" layer="96">&gt;VALUE</text>
</symbol>
<symbol name="NEO-6M_GPS">
<pin name="VCC" x="0" y="-7.62" length="middle" rot="R90"/>
<pin name="GND" x="5.08" y="-7.62" length="middle" rot="R90"/>
<pin name="TXD" x="10.16" y="-7.62" length="middle" rot="R90"/>
<pin name="RXD" x="15.24" y="-7.62" length="middle" rot="R90"/>
<pin name="PPS" x="20.32" y="-7.62" length="middle" rot="R90"/>
<wire x1="-5.08" y1="-2.54" x2="25.4" y2="-2.54" width="0.254" layer="94"/>
<wire x1="25.4" y1="-2.54" x2="25.4" y2="33.02" width="0.254" layer="94"/>
<wire x1="25.4" y1="33.02" x2="-5.08" y2="33.02" width="0.254" layer="94"/>
<wire x1="-5.08" y1="33.02" x2="-5.08" y2="-2.54" width="0.254" layer="94"/>
<text x="17.78" y="35.56" size="1.778" layer="95">&gt;NAME</text>
<text x="22.86" y="-5.08" size="1.778" layer="96">&gt;VALUE</text>
</symbol>
<symbol name="BRKWS01">
<pin name="TX" x="-10.16" y="-10.16" length="middle" rot="R90"/>
<pin name="RX" x="-7.62" y="-10.16" length="middle" rot="R90"/>
<pin name="LEDRX" x="-5.08" y="-10.16" length="middle" rot="R90"/>
<pin name="LEDTX" x="-2.54" y="-10.16" length="middle" rot="R90"/>
<pin name="NC4/DBG_EN" x="0" y="-10.16" length="middle" rot="R90"/>
<pin name="/RST" x="2.54" y="-10.16" length="middle" rot="R90"/>
<pin name="VCC" x="5.08" y="-10.16" length="middle" rot="R90"/>
<pin name="GND" x="7.62" y="-10.16" length="middle" rot="R90"/>
<pin name="GPIO0" x="17.78" y="5.08" length="middle" rot="R180"/>
<pin name="GPIO1" x="17.78" y="7.62" length="middle" rot="R180"/>
<pin name="GPIO2" x="17.78" y="10.16" length="middle" rot="R180"/>
<pin name="GPIO3" x="17.78" y="12.7" length="middle" rot="R180"/>
<pin name="GPIO9" x="-20.32" y="5.08" length="middle"/>
<pin name="RADIOLED" x="-20.32" y="7.62" length="middle"/>
<pin name="CPULED" x="-20.32" y="10.16" length="middle"/>
<pin name="GPIO4" x="-20.32" y="12.7" length="middle"/>
<pin name="GPIO5" x="-20.32" y="15.24" length="middle"/>
<pin name="GPIO6" x="-20.32" y="17.78" length="middle"/>
<pin name="GPIO7" x="-20.32" y="20.32" length="middle"/>
<pin name="GPIO8" x="-20.32" y="22.86" length="middle"/>
<wire x1="-15.24" y1="-5.08" x2="12.7" y2="-5.08" width="0.254" layer="94"/>
<wire x1="12.7" y1="-5.08" x2="12.7" y2="27.94" width="0.254" layer="94"/>
<wire x1="12.7" y1="27.94" x2="-15.24" y2="27.94" width="0.254" layer="94"/>
<wire x1="-15.24" y1="27.94" x2="-15.24" y2="-5.08" width="0.254" layer="94"/>
<text x="-15.24" y="33.02" size="1.778" layer="95">&gt;NAME</text>
<text x="-15.24" y="30.48" size="1.778" layer="96">&gt;VALUE</text>
</symbol>
<symbol name="HC05_BLUETOOTH">
<pin name="EN" x="-12.7" y="-5.08" length="middle" rot="R90"/>
<pin name="VCC" x="-7.62" y="-5.08" length="middle" rot="R90"/>
<pin name="GND" x="-2.54" y="-5.08" length="middle" rot="R90"/>
<pin name="TX" x="2.54" y="-5.08" length="middle" rot="R90"/>
<pin name="RX" x="7.62" y="-5.08" length="middle" rot="R90"/>
<pin name="STATE" x="12.7" y="-5.08" length="middle" rot="R90"/>
<wire x1="-15.24" y1="0" x2="15.24" y2="0" width="0.254" layer="94"/>
<wire x1="15.24" y1="0" x2="15.24" y2="40.64" width="0.254" layer="94"/>
<wire x1="15.24" y1="40.64" x2="-15.24" y2="40.64" width="0.254" layer="94"/>
<wire x1="-15.24" y1="40.64" x2="-15.24" y2="0" width="0.254" layer="94"/>
<text x="-15.24" y="45.72" size="1.778" layer="95">&gt;NAME</text>
<text x="-15.24" y="43.18" size="1.778" layer="96">&gt;VALUE</text>
</symbol>
</symbols>
<devicesets>
<deviceset name="ESP8266">
<gates>
<gate name="G$1" symbol="ESP8266" x="0" y="0"/>
</gates>
<devices>
<device name="" package="ESP8266">
<connects>
<connect gate="G$1" pin="CH_PD" pad="CH_PD"/>
<connect gate="G$1" pin="GND" pad="GND"/>
<connect gate="G$1" pin="GPIO0" pad="GPIO0"/>
<connect gate="G$1" pin="GPIO2" pad="GPIO2"/>
<connect gate="G$1" pin="RST" pad="RST"/>
<connect gate="G$1" pin="URXD" pad="URXD"/>
<connect gate="G$1" pin="UTXD" pad="UTXD"/>
<connect gate="G$1" pin="VCC" pad="VCC"/>
</connects>
<technologies>
<technology name=""/>
</technologies>
</device>
</devices>
</deviceset>
<deviceset name="A6_MINI_GSM-GPRS">
<gates>
<gate name="G$1" symbol="A6_MINI_GSM-GPRS" x="0" y="0"/>
</gates>
<devices>
<device name="" package="A6_MINI_GSM-GPRS">
<connects>
<connect gate="G$1" pin="EAR_L" pad="EAR_L"/>
<connect gate="G$1" pin="EAR_R" pad="EAR_R"/>
<connect gate="G$1" pin="GND" pad="GND"/>
<connect gate="G$1" pin="GPIO14" pad="GPIO14"/>
<connect gate="G$1" pin="HRX" pad="HRX"/>
<connect gate="G$1" pin="HTX" pad="HTX"/>
<connect gate="G$1" pin="INT" pad="INT"/>
<connect gate="G$1" pin="MIC+" pad="MIC+"/>
<connect gate="G$1" pin="MIC-" pad="MIC-"/>
<connect gate="G$1" pin="MIC2_P" pad="MIC2_P"/>
<connect gate="G$1" pin="NETLED" pad="NETLED"/>
<connect gate="G$1" pin="PWR" pad="PWR"/>
<connect gate="G$1" pin="RST" pad="RST"/>
<connect gate="G$1" pin="URX" pad="URX"/>
<connect gate="G$1" pin="UTX" pad="UTX"/>
<connect gate="G$1" pin="VCC" pad="VCC"/>
</connects>
<technologies>
<technology name=""/>
</technologies>
</device>
</devices>
</deviceset>
<deviceset name="NEO-6M_GPS">
<gates>
<gate name="G$1" symbol="NEO-6M_GPS" x="-10.16" y="-15.24"/>
</gates>
<devices>
<device name="" package="NEO-6M_GPS">
<connects>
<connect gate="G$1" pin="GND" pad="GND"/>
<connect gate="G$1" pin="PPS" pad="PPS"/>
<connect gate="G$1" pin="RXD" pad="RXD"/>
<connect gate="G$1" pin="TXD" pad="TXD"/>
<connect gate="G$1" pin="VCC" pad="VCC"/>
</connects>
<technologies>
<technology name=""/>
</technologies>
</device>
</devices>
</deviceset>
<deviceset name="BRKWS01">
<gates>
<gate name="G$1" symbol="BRKWS01" x="0" y="-12.7"/>
</gates>
<devices>
<device name="" package="BRKWS01">
<connects>
<connect gate="G$1" pin="/RST" pad="/RST"/>
<connect gate="G$1" pin="CPULED" pad="CPULED"/>
<connect gate="G$1" pin="GND" pad="GND"/>
<connect gate="G$1" pin="GPIO0" pad="GPIO0"/>
<connect gate="G$1" pin="GPIO1" pad="GPIO1"/>
<connect gate="G$1" pin="GPIO2" pad="GPIO2"/>
<connect gate="G$1" pin="GPIO3" pad="GPIO3"/>
<connect gate="G$1" pin="GPIO4" pad="GPIO4"/>
<connect gate="G$1" pin="GPIO5" pad="GPIO5"/>
<connect gate="G$1" pin="GPIO6" pad="GPIO6"/>
<connect gate="G$1" pin="GPIO7" pad="GPIO7"/>
<connect gate="G$1" pin="GPIO8" pad="GPIO8"/>
<connect gate="G$1" pin="GPIO9" pad="GPIO9"/>
<connect gate="G$1" pin="LEDRX" pad="LEDRX"/>
<connect gate="G$1" pin="LEDTX" pad="LEDTX"/>
<connect gate="G$1" pin="NC4/DBG_EN" pad="NC4/DBG_EN"/>
<connect gate="G$1" pin="RADIOLED" pad="RADIOLED"/>
<connect gate="G$1" pin="RX" pad="RX"/>
<connect gate="G$1" pin="TX" pad="TX"/>
<connect gate="G$1" pin="VCC" pad="VCC"/>
</connects>
<technologies>
<technology name=""/>
</technologies>
</device>
</devices>
</deviceset>
<deviceset name="HC05_BLUETOOTH">
<gates>
<gate name="G$1" symbol="HC05_BLUETOOTH" x="0" y="-20.32"/>
</gates>
<devices>
<device name="" package="HC05_BLUETOOTH">
<connects>
<connect gate="G$1" pin="EN" pad="EN"/>
<connect gate="G$1" pin="GND" pad="GND"/>
<connect gate="G$1" pin="RX" pad="RX"/>
<connect gate="G$1" pin="STATE" pad="STATE"/>
<connect gate="G$1" pin="TX" pad="TX"/>
<connect gate="G$1" pin="VCC" pad="VCC"/>
</connects>
<technologies>
<technology name=""/>
</technologies>
</device>
</devices>
</deviceset>
</devicesets>
</library>
<library name="frames" urn="urn:adsk.eagle:library:229">
<description>&lt;b&gt;Frames for Sheet and Layout&lt;/b&gt;</description>
<packages>
</packages>
<symbols>
<symbol name="DINA3_L" urn="urn:adsk.eagle:symbol:13868/1" library_version="1">
<frame x1="0" y1="0" x2="388.62" y2="264.16" columns="4" rows="4" layer="94" border-left="no" border-top="no" border-right="no" border-bottom="no"/>
</symbol>
<symbol name="DOCFIELD" urn="urn:adsk.eagle:symbol:13864/1" library_version="1">
<wire x1="0" y1="0" x2="71.12" y2="0" width="0.1016" layer="94"/>
<wire x1="101.6" y1="15.24" x2="87.63" y2="15.24" width="0.1016" layer="94"/>
<wire x1="0" y1="0" x2="0" y2="5.08" width="0.1016" layer="94"/>
<wire x1="0" y1="5.08" x2="71.12" y2="5.08" width="0.1016" layer="94"/>
<wire x1="0" y1="5.08" x2="0" y2="15.24" width="0.1016" layer="94"/>
<wire x1="101.6" y1="15.24" x2="101.6" y2="5.08" width="0.1016" layer="94"/>
<wire x1="71.12" y1="5.08" x2="71.12" y2="0" width="0.1016" layer="94"/>
<wire x1="71.12" y1="5.08" x2="87.63" y2="5.08" width="0.1016" layer="94"/>
<wire x1="71.12" y1="0" x2="101.6" y2="0" width="0.1016" layer="94"/>
<wire x1="87.63" y1="15.24" x2="87.63" y2="5.08" width="0.1016" layer="94"/>
<wire x1="87.63" y1="15.24" x2="0" y2="15.24" width="0.1016" layer="94"/>
<wire x1="87.63" y1="5.08" x2="101.6" y2="5.08" width="0.1016" layer="94"/>
<wire x1="101.6" y1="5.08" x2="101.6" y2="0" width="0.1016" layer="94"/>
<wire x1="0" y1="15.24" x2="0" y2="22.86" width="0.1016" layer="94"/>
<wire x1="101.6" y1="35.56" x2="0" y2="35.56" width="0.1016" layer="94"/>
<wire x1="101.6" y1="35.56" x2="101.6" y2="22.86" width="0.1016" layer="94"/>
<wire x1="0" y1="22.86" x2="101.6" y2="22.86" width="0.1016" layer="94"/>
<wire x1="0" y1="22.86" x2="0" y2="35.56" width="0.1016" layer="94"/>
<wire x1="101.6" y1="22.86" x2="101.6" y2="15.24" width="0.1016" layer="94"/>
<text x="1.27" y="1.27" size="2.54" layer="94">Date:</text>
<text x="12.7" y="1.27" size="2.54" layer="94">&gt;LAST_DATE_TIME</text>
<text x="72.39" y="1.27" size="2.54" layer="94">Sheet:</text>
<text x="86.36" y="1.27" size="2.54" layer="94">&gt;SHEET</text>
<text x="88.9" y="11.43" size="2.54" layer="94">REV:</text>
<text x="1.27" y="19.05" size="2.54" layer="94">TITLE:</text>
<text x="1.27" y="11.43" size="2.54" layer="94">Document Number:</text>
<text x="17.78" y="19.05" size="2.54" layer="94">&gt;DRAWING_NAME</text>
</symbol>
</symbols>
<devicesets>
<deviceset name="DINA3_L" urn="urn:adsk.eagle:component:13931/1" prefix="FRAME" uservalue="yes" library_version="1">
<description>&lt;b&gt;FRAME&lt;/b&gt;&lt;p&gt;
DIN A3, landscape with extra doc field</description>
<gates>
<gate name="G$1" symbol="DINA3_L" x="0" y="0"/>
<gate name="G$2" symbol="DOCFIELD" x="287.02" y="0" addlevel="must"/>
</gates>
<devices>
<device name="">
<technologies>
<technology name=""/>
</technologies>
</device>
</devices>
</deviceset>
</devicesets>
</library>
<library name="ic-package" urn="urn:adsk.eagle:library:239">
<description>&lt;b&gt;IC Packages an Sockets&lt;/b&gt;&lt;p&gt;
&lt;author&gt;Created by librarian@cadsoft.de&lt;/author&gt;</description>
<packages>
<package name="DIL20" urn="urn:adsk.eagle:footprint:14349/1" library_version="1">
<description>&lt;b&gt;Dual In Line Package&lt;/b&gt;</description>
<wire x1="12.7" y1="2.921" x2="-12.7" y2="2.921" width="0.1524" layer="21"/>
<wire x1="-12.7" y1="-2.921" x2="12.7" y2="-2.921" width="0.1524" layer="21"/>
<wire x1="12.7" y1="2.921" x2="12.7" y2="-2.921" width="0.1524" layer="21"/>
<wire x1="-12.7" y1="2.921" x2="-12.7" y2="1.016" width="0.1524" layer="21"/>
<wire x1="-12.7" y1="-2.921" x2="-12.7" y2="-1.016" width="0.1524" layer="21"/>
<wire x1="-12.7" y1="1.016" x2="-12.7" y2="-1.016" width="0.1524" layer="21" curve="-180"/>
<pad name="1" x="-11.43" y="-3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="2" x="-8.89" y="-3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="7" x="3.81" y="-3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="8" x="6.35" y="-3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="3" x="-6.35" y="-3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="4" x="-3.81" y="-3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="6" x="1.27" y="-3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="5" x="-1.27" y="-3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="9" x="8.89" y="-3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="10" x="11.43" y="-3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="11" x="11.43" y="3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="12" x="8.89" y="3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="13" x="6.35" y="3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="14" x="3.81" y="3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="15" x="1.27" y="3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="16" x="-1.27" y="3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="17" x="-3.81" y="3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="18" x="-6.35" y="3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="19" x="-8.89" y="3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="20" x="-11.43" y="3.81" drill="0.8128" shape="long" rot="R90"/>
<text x="-13.081" y="-3.048" size="1.27" layer="25" rot="R90">&gt;NAME</text>
<text x="-9.779" y="-0.381" size="1.27" layer="27">&gt;VALUE</text>
</package>
<package name="SOCKET-20" urn="urn:adsk.eagle:footprint:14350/1" library_version="1">
<description>&lt;b&gt;Dual In Line Socket&lt;/b&gt;</description>
<wire x1="12.7" y1="5.08" x2="-12.7" y2="5.08" width="0.1524" layer="21"/>
<wire x1="-12.7" y1="-5.08" x2="12.7" y2="-5.08" width="0.1524" layer="21"/>
<wire x1="12.7" y1="5.08" x2="12.7" y2="2.54" width="0.1524" layer="21"/>
<wire x1="-12.7" y1="5.08" x2="-12.7" y2="2.54" width="0.1524" layer="21"/>
<wire x1="-12.7" y1="-5.08" x2="-12.7" y2="-2.54" width="0.1524" layer="21"/>
<wire x1="-12.7" y1="1.016" x2="-12.7" y2="-1.016" width="0.1524" layer="21" curve="-180"/>
<wire x1="-12.7" y1="2.54" x2="12.7" y2="2.54" width="0.1524" layer="21"/>
<wire x1="-12.7" y1="2.54" x2="-12.7" y2="1.016" width="0.1524" layer="21"/>
<wire x1="12.7" y1="2.54" x2="12.7" y2="-2.54" width="0.1524" layer="21"/>
<wire x1="-12.7" y1="-2.54" x2="12.7" y2="-2.54" width="0.1524" layer="21"/>
<wire x1="-12.7" y1="-2.54" x2="-12.7" y2="-1.016" width="0.1524" layer="21"/>
<wire x1="12.7" y1="-2.54" x2="12.7" y2="-5.08" width="0.1524" layer="21"/>
<pad name="1" x="-11.43" y="-3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="2" x="-8.89" y="-3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="5" x="-1.27" y="-3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="6" x="1.27" y="-3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="3" x="-6.35" y="-3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="4" x="-3.81" y="-3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="7" x="3.81" y="-3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="8" x="6.35" y="-3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="9" x="8.89" y="-3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="10" x="11.43" y="-3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="11" x="11.43" y="3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="12" x="8.89" y="3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="13" x="6.35" y="3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="14" x="3.81" y="3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="15" x="1.27" y="3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="16" x="-1.27" y="3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="17" x="-3.81" y="3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="18" x="-6.35" y="3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="19" x="-8.89" y="3.81" drill="0.8128" shape="long" rot="R90"/>
<pad name="20" x="-11.43" y="3.81" drill="0.8128" shape="long" rot="R90"/>
<text x="-6.477" y="-0.635" size="1.27" layer="27" ratio="10">&gt;VALUE</text>
<text x="-12.954" y="-4.953" size="1.27" layer="25" ratio="10" rot="R90">&gt;NAME</text>
</package>
</packages>
<packages3d>
<package3d name="DIL20" urn="urn:adsk.eagle:package:14436/1" type="box" library_version="1">
<description>Dual In Line Package</description>
</package3d>
<package3d name="SOCKET-20" urn="urn:adsk.eagle:package:14438/1" type="box" library_version="1">
<description>Dual In Line Socket</description>
</package3d>
</packages3d>
<symbols>
<symbol name="DIL20" urn="urn:adsk.eagle:symbol:14348/1" library_version="1">
<wire x1="-5.08" y1="11.43" x2="-5.08" y2="-13.97" width="0.254" layer="94"/>
<wire x1="-5.08" y1="-13.97" x2="5.08" y2="-13.97" width="0.254" layer="94"/>
<wire x1="5.08" y1="-13.97" x2="5.08" y2="11.43" width="0.254" layer="94"/>
<wire x1="5.08" y1="11.43" x2="2.54" y2="11.43" width="0.254" layer="94"/>
<wire x1="-5.08" y1="11.43" x2="-2.54" y2="11.43" width="0.254" layer="94"/>
<wire x1="-2.54" y1="11.43" x2="2.54" y2="11.43" width="0.254" layer="94" curve="180"/>
<text x="-4.445" y="12.065" size="1.778" layer="95">&gt;NAME</text>
<text x="-4.445" y="-16.51" size="1.778" layer="96">&gt;VALUE</text>
<pin name="1" x="-7.62" y="10.16" visible="pad" length="short" direction="pas"/>
<pin name="2" x="-7.62" y="7.62" visible="pad" length="short" direction="pas"/>
<pin name="3" x="-7.62" y="5.08" visible="pad" length="short" direction="pas"/>
<pin name="4" x="-7.62" y="2.54" visible="pad" length="short" direction="pas"/>
<pin name="5" x="-7.62" y="0" visible="pad" length="short" direction="pas"/>
<pin name="6" x="-7.62" y="-2.54" visible="pad" length="short" direction="pas"/>
<pin name="7" x="-7.62" y="-5.08" visible="pad" length="short" direction="pas"/>
<pin name="8" x="-7.62" y="-7.62" visible="pad" length="short" direction="pas"/>
<pin name="9" x="-7.62" y="-10.16" visible="pad" length="short" direction="pas"/>
<pin name="10" x="-7.62" y="-12.7" visible="pad" length="short" direction="pas"/>
<pin name="11" x="7.62" y="-12.7" visible="pad" length="short" direction="pas" rot="R180"/>
<pin name="12" x="7.62" y="-10.16" visible="pad" length="short" direction="pas" rot="R180"/>
<pin name="13" x="7.62" y="-7.62" visible="pad" length="short" direction="pas" rot="R180"/>
<pin name="14" x="7.62" y="-5.08" visible="pad" length="short" direction="pas" rot="R180"/>
<pin name="15" x="7.62" y="-2.54" visible="pad" length="short" direction="pas" rot="R180"/>
<pin name="16" x="7.62" y="0" visible="pad" length="short" direction="pas" rot="R180"/>
<pin name="17" x="7.62" y="2.54" visible="pad" length="short" direction="pas" rot="R180"/>
<pin name="18" x="7.62" y="5.08" visible="pad" length="short" direction="pas" rot="R180"/>
<pin name="19" x="7.62" y="7.62" visible="pad" length="short" direction="pas" rot="R180"/>
<pin name="20" x="7.62" y="10.16" visible="pad" length="short" direction="pas" rot="R180"/>
</symbol>
</symbols>
<devicesets>
<deviceset name="DIL20" urn="urn:adsk.eagle:component:14473/1" prefix="IC" uservalue="yes" library_version="1">
<description>&lt;b&gt;Dual In Line / Socket&lt;/b&gt;</description>
<gates>
<gate name="G$1" symbol="DIL20" x="0" y="0"/>
</gates>
<devices>
<device name="" package="DIL20">
<connects>
<connect gate="G$1" pin="1" pad="1"/>
<connect gate="G$1" pin="10" pad="10"/>
<connect gate="G$1" pin="11" pad="11"/>
<connect gate="G$1" pin="12" pad="12"/>
<connect gate="G$1" pin="13" pad="13"/>
<connect gate="G$1" pin="14" pad="14"/>
<connect gate="G$1" pin="15" pad="15"/>
<connect gate="G$1" pin="16" pad="16"/>
<connect gate="G$1" pin="17" pad="17"/>
<connect gate="G$1" pin="18" pad="18"/>
<connect gate="G$1" pin="19" pad="19"/>
<connect gate="G$1" pin="2" pad="2"/>
<connect gate="G$1" pin="20" pad="20"/>
<connect gate="G$1" pin="3" pad="3"/>
<connect gate="G$1" pin="4" pad="4"/>
<connect gate="G$1" pin="5" pad="5"/>
<connect gate="G$1" pin="6" pad="6"/>
<connect gate="G$1" pin="7" pad="7"/>
<connect gate="G$1" pin="8" pad="8"/>
<connect gate="G$1" pin="9" pad="9"/>
</connects>
<package3dinstances>
<package3dinstance package3d_urn="urn:adsk.eagle:package:14436/1"/>
</package3dinstances>
<technologies>
<technology name=""/>
</technologies>
</device>
<device name="S" package="SOCKET-20">
<connects>
<connect gate="G$1" pin="1" pad="1"/>
<connect gate="G$1" pin="10" pad="10"/>
<connect gate="G$1" pin="11" pad="11"/>
<connect gate="G$1" pin="12" pad="12"/>
<connect gate="G$1" pin="13" pad="13"/>
<connect gate="G$1" pin="14" pad="14"/>
<connect gate="G$1" pin="15" pad="15"/>
<connect gate="G$1" pin="16" pad="16"/>
<connect gate="G$1" pin="17" pad="17"/>
<connect gate="G$1" pin="18" pad="18"/>
<connect gate="G$1" pin="19" pad="19"/>
<connect gate="G$1" pin="2" pad="2"/>
<connect gate="G$1" pin="20" pad="20"/>
<connect gate="G$1" pin="3" pad="3"/>
<connect gate="G$1" pin="4" pad="4"/>
<connect gate="G$1" pin="5" pad="5"/>
<connect gate="G$1" pin="6" pad="6"/>
<connect gate="G$1" pin="7" pad="7"/>
<connect gate="G$1" pin="8" pad="8"/>
<connect gate="G$1" pin="9" pad="9"/>
</connects>
<package3dinstances>
<package3dinstance package3d_urn="urn:adsk.eagle:package:14438/1"/>
</package3dinstances>
<technologies>
<technology name=""/>
</technologies>
</device>
</devices>
</deviceset>
</devicesets>
</library>
</libraries>
<attributes>
</attributes>
<variantdefs>
</variantdefs>
<classes>
<class number="0" name="default" width="0" drill="0">
</class>
</classes>
<parts>
<part name="WIFI" library="jens" deviceset="ESP8266" device=""/>
<part name="GSM/GPRS" library="jens" deviceset="A6_MINI_GSM-GPRS" device=""/>
<part name="FRAME1" library="frames" library_urn="urn:adsk.eagle:library:229" deviceset="DINA3_L" device=""/>
<part name="GPS" library="jens" deviceset="NEO-6M_GPS" device=""/>
<part name="SIGFOX" library="jens" deviceset="BRKWS01" device=""/>
<part name="PIC16F15344" library="ic-package" library_urn="urn:adsk.eagle:library:239" deviceset="DIL20" device="" package3d_urn="urn:adsk.eagle:package:14436/1"/>
<part name="BLUETOOTH" library="jens" deviceset="HC05_BLUETOOTH" device=""/>
</parts>
<sheets>
<sheet>
<plain>
</plain>
<instances>
<instance part="WIFI" gate="G$1" x="0" y="0"/>
<instance part="GSM/GPRS" gate="G$1" x="0" y="48.26"/>
<instance part="FRAME1" gate="G$1" x="-187.96" y="-121.92"/>
<instance part="FRAME1" gate="G$2" x="99.06" y="-121.92"/>
<instance part="GPS" gate="G$1" x="63.5" y="35.56"/>
<instance part="SIGFOX" gate="G$1" x="73.66" y="-30.48"/>
<instance part="PIC16F15344" gate="G$1" x="-88.9" y="55.88"/>
<instance part="BLUETOOTH" gate="G$1" x="-83.82" y="-27.94"/>
</instances>
<busses>
</busses>
<nets>
<net name="WIFI-TX" class="0">
<segment>
<pinref part="WIFI" gate="G$1" pin="UTXD"/>
<wire x1="-15.24" y1="7.62" x2="-22.86" y2="7.62" width="0.1524" layer="91"/>
<label x="-22.86" y="7.62" size="1.778" layer="95" rot="R180" xref="yes"/>
</segment>
<segment>
<pinref part="PIC16F15344" gate="G$1" pin="2"/>
<wire x1="-96.52" y1="63.5" x2="-111.76" y2="63.5" width="0.1524" layer="91"/>
<label x="-111.76" y="63.5" size="1.778" layer="95" rot="R180" xref="yes"/>
</segment>
</net>
<net name="3.3V" class="0">
<segment>
<pinref part="WIFI" gate="G$1" pin="VCC"/>
<wire x1="-15.24" y1="-7.62" x2="-22.86" y2="-7.62" width="0.1524" layer="91"/>
<label x="-22.86" y="-7.62" size="1.778" layer="95" rot="R180" xref="yes"/>
</segment>
<segment>
<pinref part="WIFI" gate="G$1" pin="CH_PD"/>
<wire x1="-15.24" y1="2.54" x2="-22.86" y2="2.54" width="0.1524" layer="91"/>
<label x="-22.86" y="2.54" size="1.778" layer="95" rot="R180" xref="yes"/>
</segment>
<segment>
<pinref part="SIGFOX" gate="G$1" pin="VCC"/>
<wire x1="78.74" y1="-40.64" x2="78.74" y2="-55.88" width="0.1524" layer="91"/>
<label x="78.74" y="-55.88" size="1.778" layer="95" rot="R270" xref="yes"/>
</segment>
<segment>
<pinref part="PIC16F15344" gate="G$1" pin="1"/>
<wire x1="-96.52" y1="66.04" x2="-99.06" y2="66.04" width="0.1524" layer="91"/>
<label x="-99.06" y="66.04" size="1.778" layer="95" rot="R180" xref="yes"/>
</segment>
</net>
<net name="GND" class="0">
<segment>
<pinref part="WIFI" gate="G$1" pin="GND"/>
<wire x1="15.24" y1="7.62" x2="20.32" y2="7.62" width="0.1524" layer="91"/>
<label x="20.32" y="7.62" size="1.778" layer="95" xref="yes"/>
</segment>
<segment>
<pinref part="GSM/GPRS" gate="G$1" pin="GND"/>
<wire x1="17.78" y1="55.88" x2="25.4" y2="55.88" width="0.1524" layer="91"/>
<label x="25.4" y="55.88" size="1.778" layer="95" xref="yes"/>
</segment>
<segment>
<pinref part="GPS" gate="G$1" pin="GND"/>
<wire x1="68.58" y1="27.94" x2="68.58" y2="20.32" width="0.1524" layer="91"/>
<label x="68.58" y="20.32" size="1.778" layer="95" rot="R270" xref="yes"/>
</segment>
<segment>
<pinref part="SIGFOX" gate="G$1" pin="GND"/>
<wire x1="81.28" y1="-40.64" x2="81.28" y2="-55.88" width="0.1524" layer="91"/>
<label x="81.28" y="-55.88" size="1.778" layer="95" xref="yes"/>
</segment>
<segment>
<pinref part="BLUETOOTH" gate="G$1" pin="GND"/>
<wire x1="-86.36" y1="-33.02" x2="-86.36" y2="-40.64" width="0.1524" layer="91"/>
<label x="-86.36" y="-40.64" size="1.778" layer="95" rot="R270" xref="yes"/>
</segment>
<segment>
<pinref part="PIC16F15344" gate="G$1" pin="20"/>
<wire x1="-81.28" y1="66.04" x2="-78.74" y2="66.04" width="0.1524" layer="91"/>
<label x="-78.74" y="66.04" size="1.778" layer="95" xref="yes"/>
</segment>
</net>
<net name="WIFI-RX" class="0">
<segment>
<pinref part="WIFI" gate="G$1" pin="URXD"/>
<wire x1="15.24" y1="-7.62" x2="20.32" y2="-7.62" width="0.1524" layer="91"/>
<label x="20.32" y="-7.62" size="1.778" layer="95" xref="yes"/>
</segment>
<segment>
<pinref part="PIC16F15344" gate="G$1" pin="3"/>
<wire x1="-96.52" y1="60.96" x2="-99.06" y2="60.96" width="0.1524" layer="91"/>
<label x="-99.06" y="60.96" size="1.778" layer="95" rot="R180" xref="yes"/>
</segment>
</net>
<net name="5V" class="0">
<segment>
<pinref part="GSM/GPRS" gate="G$1" pin="VCC"/>
<wire x1="-17.78" y1="66.04" x2="-25.4" y2="66.04" width="0.1524" layer="91"/>
<label x="-25.4" y="66.04" size="1.778" layer="95" rot="R180" xref="yes"/>
</segment>
<segment>
<pinref part="GPS" gate="G$1" pin="VCC"/>
<wire x1="63.5" y1="27.94" x2="63.5" y2="20.32" width="0.1524" layer="91"/>
<label x="63.5" y="20.32" size="1.778" layer="95" rot="R270" xref="yes"/>
</segment>
<segment>
<pinref part="BLUETOOTH" gate="G$1" pin="VCC"/>
<wire x1="-91.44" y1="-33.02" x2="-91.44" y2="-40.64" width="0.1524" layer="91"/>
<label x="-91.44" y="-40.64" size="1.778" layer="95" rot="R270" xref="yes"/>
</segment>
</net>
<net name="GSM-RX" class="0">
<segment>
<pinref part="GSM/GPRS" gate="G$1" pin="URX"/>
<wire x1="17.78" y1="50.8" x2="25.4" y2="50.8" width="0.1524" layer="91"/>
<label x="25.4" y="50.8" size="1.778" layer="95" xref="yes"/>
</segment>
<segment>
<pinref part="PIC16F15344" gate="G$1" pin="15"/>
<wire x1="-81.28" y1="53.34" x2="-66.04" y2="53.34" width="0.1524" layer="91"/>
<label x="-66.04" y="53.34" size="1.778" layer="95" xref="yes"/>
</segment>
</net>
<net name="GSM-TX" class="0">
<segment>
<pinref part="GSM/GPRS" gate="G$1" pin="UTX"/>
<wire x1="17.78" y1="45.72" x2="25.4" y2="45.72" width="0.1524" layer="91"/>
<label x="25.4" y="45.72" size="1.778" layer="95" xref="yes"/>
</segment>
<segment>
<pinref part="PIC16F15344" gate="G$1" pin="16"/>
<wire x1="-81.28" y1="55.88" x2="-78.74" y2="55.88" width="0.1524" layer="91"/>
<label x="-78.74" y="55.88" size="1.778" layer="95" xref="yes"/>
</segment>
</net>
<net name="GPS-TX" class="0">
<segment>
<pinref part="GPS" gate="G$1" pin="TXD"/>
<wire x1="73.66" y1="27.94" x2="73.66" y2="20.32" width="0.1524" layer="91"/>
<label x="73.66" y="20.32" size="1.778" layer="95" rot="R270" xref="yes"/>
</segment>
<segment>
<pinref part="PIC16F15344" gate="G$1" pin="5"/>
<wire x1="-96.52" y1="55.88" x2="-99.06" y2="55.88" width="0.1524" layer="91"/>
<label x="-99.06" y="55.88" size="1.778" layer="95" rot="R180" xref="yes"/>
</segment>
</net>
<net name="GPS-RX" class="0">
<segment>
<pinref part="GPS" gate="G$1" pin="RXD"/>
<wire x1="78.74" y1="27.94" x2="78.74" y2="20.32" width="0.1524" layer="91"/>
<label x="78.74" y="20.32" size="1.778" layer="95" rot="R270" xref="yes"/>
</segment>
<segment>
<pinref part="PIC16F15344" gate="G$1" pin="6"/>
<wire x1="-96.52" y1="53.34" x2="-116.84" y2="53.34" width="0.1524" layer="91"/>
<label x="-116.84" y="53.34" size="1.778" layer="95" rot="R180" xref="yes"/>
</segment>
</net>
<net name="SIGFOX-TX" class="0">
<segment>
<pinref part="SIGFOX" gate="G$1" pin="TX"/>
<wire x1="63.5" y1="-40.64" x2="63.5" y2="-55.88" width="0.1524" layer="91"/>
<label x="63.5" y="-55.88" size="1.778" layer="95" rot="R180" xref="yes"/>
</segment>
<segment>
<pinref part="PIC16F15344" gate="G$1" pin="7"/>
<wire x1="-96.52" y1="50.8" x2="-99.06" y2="50.8" width="0.1524" layer="91"/>
<label x="-99.06" y="50.8" size="1.778" layer="95" rot="R180" xref="yes"/>
</segment>
</net>
<net name="SIGFOX-RX" class="0">
<segment>
<pinref part="SIGFOX" gate="G$1" pin="RX"/>
<wire x1="66.04" y1="-40.64" x2="66.04" y2="-55.88" width="0.1524" layer="91"/>
<label x="66.04" y="-55.88" size="1.778" layer="95" rot="R270" xref="yes"/>
</segment>
<segment>
<pinref part="PIC16F15344" gate="G$1" pin="8"/>
<wire x1="-96.52" y1="48.26" x2="-116.84" y2="48.26" width="0.1524" layer="91"/>
<label x="-116.84" y="48.26" size="1.778" layer="95" rot="R180" xref="yes"/>
</segment>
</net>
<net name="BT-TX" class="0">
<segment>
<pinref part="BLUETOOTH" gate="G$1" pin="TX"/>
<wire x1="-81.28" y1="-33.02" x2="-81.28" y2="-40.64" width="0.1524" layer="91"/>
<label x="-81.28" y="-40.64" size="1.778" layer="95" rot="R270" xref="yes"/>
</segment>
<segment>
<pinref part="PIC16F15344" gate="G$1" pin="12"/>
<wire x1="-81.28" y1="45.72" x2="-78.74" y2="45.72" width="0.1524" layer="91"/>
<label x="-78.74" y="45.72" size="1.778" layer="95" xref="yes"/>
</segment>
</net>
<net name="BT-RX" class="0">
<segment>
<pinref part="BLUETOOTH" gate="G$1" pin="RX"/>
<wire x1="-76.2" y1="-33.02" x2="-76.2" y2="-40.64" width="0.1524" layer="91"/>
<label x="-76.2" y="-40.64" size="1.778" layer="95" rot="R270" xref="yes"/>
</segment>
<segment>
<pinref part="PIC16F15344" gate="G$1" pin="11"/>
<wire x1="-81.28" y1="43.18" x2="-68.58" y2="43.18" width="0.1524" layer="91"/>
<label x="-68.58" y="43.18" size="1.778" layer="95" xref="yes"/>
</segment>
</net>
<net name="VPP" class="0">
<segment>
<pinref part="PIC16F15344" gate="G$1" pin="4"/>
<wire x1="-96.52" y1="58.42" x2="-111.76" y2="58.42" width="0.1524" layer="91"/>
<label x="-111.76" y="58.42" size="1.778" layer="95" rot="R180" xref="yes"/>
</segment>
</net>
<net name="ICSPDAT" class="0">
<segment>
<pinref part="PIC16F15344" gate="G$1" pin="19"/>
<wire x1="-81.28" y1="63.5" x2="-63.5" y2="63.5" width="0.1524" layer="91"/>
<label x="-63.5" y="63.5" size="1.778" layer="95" xref="yes"/>
</segment>
</net>
<net name="ICSPCLK" class="0">
<segment>
<pinref part="PIC16F15344" gate="G$1" pin="18"/>
<wire x1="-81.28" y1="60.96" x2="-78.74" y2="60.96" width="0.1524" layer="91"/>
<label x="-78.74" y="60.96" size="1.778" layer="95" xref="yes"/>
</segment>
</net>
</nets>
</sheet>
</sheets>
</schematic>
</drawing>
<compatibility>
<note version="8.2" severity="warning">
Since Version 8.2, EAGLE supports online libraries. The ids
of those online libraries will not be understood (or retained)
with this version.
</note>
<note version="8.3" severity="warning">
Since Version 8.3, EAGLE supports URNs for individual library
assets (packages, symbols, and devices). The URNs of those assets
will not be understood (or retained) with this version.
</note>
<note version="8.3" severity="warning">
Since Version 8.3, EAGLE supports the association of 3D packages
with devices in libraries, schematics, and board files. Those 3D
packages will not be understood (or retained) with this version.
</note>
</compatibility>
</eagle>
