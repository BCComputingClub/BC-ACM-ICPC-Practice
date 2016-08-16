<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:svg="http://www.w3.org/2000/svg"
                version="1.0">

  <xsl:output method="text"/> 

<!--
   Simple hack for generating Funhouse data sets.

   1) Create a layout in Dia (start from grid.dia)
      using only line segments, snapped to grid
      Black: walls, Red: exits, Green: entrances, other: doors

   2) Export as "svg Plain"

   3) Use this style sheet to convert to a data file:
       xsltproc svg2data.xsl yourLayout.svg > yourLayout.in

-->


  <xsl:template match="/">
    <xsl:apply-templates/>
  </xsl:template>

  <xsl:template match="svg:svg">
    <xsl:value-of select="count(svg:line)"/>
    <xsl:text>&#10;</xsl:text>
    <xsl:apply-templates select="svg:line"/>
    <xsl:text>0&#10;</xsl:text>
  </xsl:template>

  <xsl:template match="svg:line">
    <xsl:value-of select="round(@x1)"/>
    <xsl:text> </xsl:text>
    <xsl:value-of select="round(@y1)"/>
    <xsl:text> </xsl:text>
    <xsl:value-of select="round(@x2)"/>
    <xsl:text> </xsl:text>
    <xsl:value-of select="round(@y2)"/>
    <xsl:choose>
      <xsl:when test="@stroke = '#000000'">
	<xsl:text> W</xsl:text>
      </xsl:when>
      <xsl:when test="@stroke = '#FF0000'">
	<xsl:text> X</xsl:text>
      </xsl:when>
      <xsl:when test="@stroke = '#00FF00'">
	<xsl:text> E</xsl:text>
      </xsl:when>
      <xsl:otherwise>
	<xsl:text> D</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:text>&#10;</xsl:text>
  </xsl:template>

  <xsl:template match="*|text()">
  </xsl:template>

</xsl:stylesheet>
