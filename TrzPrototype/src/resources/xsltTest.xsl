<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:output method="xml" version="1.0" encoding="UTF-8"
		indent="no" />
	<xsl:decimal-format name="commaspace"
		decimal-separator="," grouping-separator=" " NaN="" />
	<xsl:decimal-format name="commadot"
		decimal-separator="," grouping-separator="." NaN="" />
	<xsl:decimal-format name="dotcomma"
		decimal-separator="." grouping-separator="," NaN="" />
	<xsl:output method="xml" version="1.0" omit-xml-declaration="no"
		indent="yes" />
	<xsl:param name="paramPeriod" select="'1.0'" />
	<xsl:param name="paramEmployee" select="'1.0'" />
	<xsl:param name="paramDepartment" select="'1.0'" />
	<xsl:param name="paramOthers" select="'1.0'" />
	<xsl:param name="paramVauch1" select="'1.0'" />
	<xsl:param name="paramVauch2" select="'1.0'" />
	

	<!-- ========================= -->
	<!-- root element: projectteam -->
	<!-- ========================= -->
	<xsl:template match="/v">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format"
			font-family="Arial">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="simpleA4"
					page-height="29.7cm" page-width="21cm" margin-top="2cm"
					margin-bottom="2cm" margin-left="2cm" margin-right="2cm">
					<fo:region-body />
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="simpleA4">
				<fo:flow flow-name="xsl-region-body">
				    <xsl:variable name="v1"  select="v1" />
				    <xsl:variable name="v1"  select="v2" />
				    <xsl:variable name="v8"  select="v8" />
				    <xsl:variable name="v10"  select="v10" />
				    <xsl:variable name="v11"  select="v11" />
					<fo:block >
						<fo:table table-layout="fixed" width="80%"
							 border="1pt solid black" font-size="8pt" line-height="10pt">
        					<fo:table-column column-width="60%"
								number-columns-spanned="4"  />
							<fo:table-column column-width="30%"
								number-columns-spanned="4"  />
							<fo:table-column column-width="10%"
								number-columns-spanned="4"  />
							<fo:table-body>
								<fo:table-row  row-height = "40mm" border="0.5pt solid black">
									<fo:table-cell font-weight="bold" text-align="center" border="0.5pt solid black"
                            padding-left="3pt" padding-right="3pt" padding-top="1pt" padding-bottom="1pt">
										<fo:block >
											Заплата за месец
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										     <xsl:value-of select="$paramPeriod" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								
								<fo:table-row border="0.5pt solid black">
									<fo:table-cell font-weight="bold" text-align="center" border="0.5pt solid black"
                            padding-left="3pt" padding-right="3pt" padding-top="1pt" padding-bottom="1pt">
										<fo:block>
											Име фамилия
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										    	<xsl:value-of select="$paramEmployee" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								
								<fo:table-row border="0.5pt solid black">
									<fo:table-cell font-weight="bold" text-align="center" border="0.5pt solid black"
                            padding-left="3pt" padding-right="3pt" padding-top="1pt" padding-bottom="1pt">
										<fo:block>
											Отдел
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										    	<xsl:value-of select="$paramDepartment" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								
								
								<fo:table-row border="0.5pt solid black">
									<fo:table-cell >
										<fo:block>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								
								<fo:table-row border="0.5pt solid black">
									<fo:table-cell >
										<fo:block>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell font-weight="bold" text-align="center" border="0.5pt solid black"
                            padding-left="3pt" padding-right="3pt" padding-top="1pt" padding-bottom="1pt">
										<fo:block>
										   Нетна заплата
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										  <xsl:value-of select="v1" />
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row border="0.5pt solid black">
									<fo:table-cell >
										<fo:block>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell font-weight="bold" text-align="center" border="0.5pt solid black"
                            padding-left="3pt" padding-right="3pt" padding-top="1pt" padding-bottom="1pt">
										<fo:block>
										    Бруто заплата
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										  <xsl:value-of select="v3" />
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
									<fo:table-row border="0.5pt solid black">
									<fo:table-cell >
										<fo:block>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell font-weight="italic" text-align="center" border="0.5pt solid black"
                            padding-left="3pt" padding-right="3pt" padding-top="1pt" padding-bottom="1pt">
										<fo:block>
										   Премия тип за месец (изписва се месеца за който е начислена премията, тоест в януарскатазаплата на това място ще пише декември)
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										    <xsl:value-of select="v8" />
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								
								
								<fo:table-row border="0.5pt solid black">
									<fo:table-cell >
										<fo:block>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell font-weight="italic" text-align="center" border="0.5pt solid black"
                            padding-left="3pt" padding-right="3pt" padding-top="1pt" padding-bottom="1pt">
										<fo:block>
										  Доплащане до гарантирана минимална сума
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										     <xsl:value-of select="v10" />
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								
								<fo:table-row border="0.5pt solid black">
									<fo:table-cell >
										<fo:block>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell font-weight="italic" text-align="center" border="0.5pt solid black"
                            padding-left="3pt" padding-right="3pt" padding-top="1pt" padding-bottom="1pt">
										<fo:block>
										Специална премия + текст за специална премия
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										     <xsl:value-of select="v11" />
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								
								<fo:table-row border="0.5pt solid black">
									<fo:table-cell >
										<fo:block>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								
								<fo:table-row border="0.5pt solid black">
									<fo:table-cell >
										<fo:block>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								
									<fo:table-row border="0.5pt solid black">
									<fo:table-cell font-weight="italic" text-align="center" border="0.5pt solid black"
                            padding-left="3pt" padding-right="3pt" padding-top="1pt" padding-bottom="1pt">
										<fo:block>
										   за получаване по
										</fo:block>
									</fo:table-cell>
									<fo:table-cell font-weight="italic" text-align="center" border="0.5pt solid black"
                            padding-left="3pt" padding-right="3pt" padding-top="1pt" padding-bottom="1pt">
										<fo:block>
										карта
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										   <xsl:value-of select="v2" />
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								
								<fo:table-row border="0.5pt solid black">
									<fo:table-cell font-weight="italic" text-align="center" border="0.5pt solid black"
                            padding-left="3pt" padding-right="3pt" padding-top="1pt" padding-bottom="1pt">
										<fo:block>
										   за получаване
										</fo:block>
									</fo:table-cell>
									<fo:table-cell font-weight="italic" text-align="center" border="0.5pt solid black"
                            padding-left="3pt" padding-right="3pt" padding-top="1pt" padding-bottom="1pt">
										<fo:block>
										други
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										    <xsl:value-of select="$paramOthers" />
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								
								<fo:table-row border="0.5pt solid black">
									<fo:table-cell>
										<fo:block>
										  
										</fo:block>
									</fo:table-cell>
									<fo:table-cell font-weight="italic" text-align="center" border="0.5pt solid black"
                            padding-left="3pt" padding-right="3pt" padding-top="1pt" padding-bottom="1pt">
										<fo:block>
										 Общо нето за получаване
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										   1429
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								
								
								<fo:table-row border="0.5pt solid black">
									<fo:table-cell >
										<fo:block>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								
								<fo:table-row border="0.5pt solid black">
									<fo:table-cell>
										<fo:block>
										  
										</fo:block>
									</fo:table-cell>
									<fo:table-cell font-weight="italic" text-align="center" border="0.5pt solid black"
                            padding-left="3pt" padding-right="3pt" padding-top="1pt" padding-bottom="1pt">
										<fo:block>
										 Ваучер 1
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										   <xsl:value-of select="$paramVauch1" />
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								
								
								<fo:table-row border="0.5pt solid black">
									<fo:table-cell>
										<fo:block>
										  
										</fo:block>
									</fo:table-cell>
									<fo:table-cell font-weight="italic" text-align="center" border="0.5pt solid black"
                            padding-left="3pt" padding-right="3pt" padding-top="1pt" padding-bottom="1pt">
										<fo:block>
										 Ваучер 2
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
										   <xsl:value-of select="$paramVauch2" />
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								
							</fo:table-body>
						</fo:table>
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<!-- ========================= -->
	<!-- child element: member -->
	<!-- ========================= -->
	<xsl:template match="v">

	</xsl:template>
</xsl:stylesheet>