<%--
###################################################################################
ee.jsp

Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
University of Utah, Salt Lake City, UT 84112

Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i>
Academic Outreach and Continuing Education (AOCE)
1901 East South Campus Dr., Room 2197-E
    
Protected by U.S. Provisional Patent U-4003, February 2006

Displays the WebEQ Equation Editor (EE) input controllet applet.
###################################################################################
--%>

<%@ tag display-name="Prints the parent hierarchy of a catalog item"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- ============ Tag input attributes ============ --%>

<%-- ============ Tag input fragment attributes ============ --%>
<%-- Name of form bean whose property is set to the output of the equation editor --%>
<%@ attribute name="name" required="true"%>

<%-- Form bean property to set to the output of the equation editor --%>
<%@ attribute name="property" required="true"%>

<%-- Name to provide the applet element --%>
<%@ attribute name="applet" required="true"%>

<%-- ============ Tag output variables ============ --%>
  
<%--============================ Useful definitions ==============================--%>

<%--
Uncomment this section to turn on XHTML validation of this file.
Because this is a JSP snippet, make sure to comment it back out
before running it on the application container.
--%>
<%--
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
--%>

<%-- Declare that this JSP snippet/included JSP adheres to XHTML syntax. --%>
<html:xhtml />

<%-- ============ Tag code begins here ============ --%>

<applet codebase="/ru2/lib" archive="WebEQApplet.jar"
	code="webeq3.editor.InputControl" width="485" height="115"
	name="${applet}" id="${applet}">
	<param name="useslibrary" value="WebEQApplet">
	<param name="useslibrarycodebase" value="WebEQApplet.cab">
	<param name="useslibraryversion" value="3,7,0,0">
	<param name="toolbar"
		value='
	<%-- Standard toolbar --%>
	<%-- <tb><incl name='#standard'/></tb> --%>
	
	<%-- Our custom toolbar --%>
	<tb>
	<btn>LAYOUTPALETTE</btn><sub cols="3"><incl name="#layout"/></sub>
	<btn>TRIGPALETTE</btn><sub size="w" cols="3"><incl name="#trig"/></sub>
	<btn>MATRIXPALETTE</btn><sub><incl name="#matrix"/></sub>
	
<%--  	<sep/> --%>
	
	<btn>ARROWPALETTE</btn>
	<sub size="s" cols="5">
	  <btn>rarr</btn><btn>larr</btn><btn>rArr</btn><btn>lArr</btn>
	  <btn>uarr</btn><btn>darr</btn><btn>harr</btn><btn>hArr</btn>
	  <btn>varr</btn><btn>searr</btn><btn>swarr</btn><btn>nwarr</btn>
	  <btn>nearr</btn><btn>rarrhk</btn><btn>larrhk</btn><btn>map</btn>
	  <btn>rharu</btn><btn>lharu</btn><btn>harrw</btn><btn>zigrarr</btn>
	  <btn>nlArr</btn><btn>nhArr</btn><btn>nrArr</btn><btn>uArr</btn>
	  <btn>dArr</btn><btn>vArr</btn><btn>hellip</btn><btn>vellip</btn>
	  <btn>ctdot</btn><btn>dtdot</btn>
	</sub>
	
	<btn>OPERATORPALETTE</btn>
	<sub size="s" cols="6">
	  <btn>plus</btn><btn>minus</btn>
	  <btn>times</btn><btn>divide</btn><btn>sdot</btn><btn>plusmn</btn>
	  <btn>oplus</btn><btn>otimes</btn><btn>par</btn><btn>npar</btn>
	  <btn>compfn</btn><btn>perp</btn><btn>prop</btn><btn>nmid</btn>
	  <btn>wreath</btn><btn>fork</btn><btn>mumap</btn><btn>plusdo</btn>
	  <btn>flat</btn><btn>natur</btn><btn>sharp</btn>
	  <btn>utrif</btn><btn>utri</btn><btn>rtrif</btn><btn>rtri</btn>
	  <btn>dtrif</btn><btn>dtri</btn><btn>ltrif</btn><btn>ltri</btn>
	  <btn>squ</btn><btn>squf</btn><btn>plusb</btn><btn>minusb</btn>
	  <btn>timesb</btn><btn>sdotb</btn><btn>oplus</btn><btn>ominus</btn>
	  <btn>otimes</btn><btn>osol</btn><btn>odot</btn><btn>bull</btn><btn>sdot</btn>
	</sub>
	
	<btn>RELATIONPALETTE</btn>
	<sub size="s" cols="6">
	  <btn>lt</btn><btn>gt</btn><btn>le</btn><btn>ge</btn>
	  <btn>nlt</btn><btn>ngt</btn><btn>nle</btn><btn>nge</btn>
	  <btn>lne</btn><btn>gne</btn><btn>equals</btn><btn>equiv</btn>
	  <btn>cong</btn><btn>ap</btn><btn>sim</btn><btn>neq</btn>
	  <btn>nequiv</btn><btn>ncong</btn><btn>nap</btn><btn>nsim</btn>
	  <btn>nlE</btn><btn>ngE</btn><btn>ltdot</btn>
	  <btn>gtdot</btn><btn>Lt</btn><btn>Gt</btn><btn>Ll</btn><btn>Gg</btn>
	  <btn>pr</btn><btn>sc</btn><btn>npr</btn><btn>nsc</btn><btn>npre</btn>
	  <btn>nsce</btn><btn>prE</btn><btn>sccue</btn>
	  <btn>simeq</btn><btn>nsimeq</btn><btn>simne</btn><btn>asymp</btn>
	  <btn>NotCupCap</btn><btn>cire</btn><btn>wedgeq</btn><btn>veeeq</btn>
	  <btn>trie</btn><btn>nrtrie</btn><btn>equest</btn>
	  <btn>ltrie</btn><btn>rtrie</btn><btn>ltimes</btn>
	  <btn>rtimes</btn><btn>nltri</btn><btn>nrtri</btn><btn>nltrie</btn>
	</sub>
	
	<%--
	<btn>SETTHEORYPALETTE</btn><sub size="s"><incl name="#settheory"/></sub>
	--%>
	
	<btn>LOGICPALETTE</btn>
	<sub size="s" cols="3">
	  <incl name="#logic"/>
	  <incl name="#dashes"/>
	  <btn>there4</btn><btn>becaus</btn>
	</sub>
	
	<btn>LCGREEKPALETTE</btn>
	<sub>
	  <btn>LCGREEKPALETTE</btn><sub size="s"><incl name="#greek"/></sub>
	  <btn>UCGREEKPALETTE</btn><sub size="s" inset=6><incl name="#Greek"/></sub>
	</sub>
	
	<btn>INVISIBLECHARSPALETTE</btn><sub size="ws" cols="2"><incl name="#invisiblechars"/></sub>
	
	<sep/>
	
	<btn>TOOLBOX</btn><sub cols="3"><incl name="#toolbox"/></sub>
	<btn>CHECKSYNTAX</btn>
	<btn>HELP</btn>
	</tb>	
	' />
	<param name="eq"
		value="<bean:write name='${name}' property='${property}' />">
</applet>
