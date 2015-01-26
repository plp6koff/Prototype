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
					<fo:block font-weight="bold">
						Фиш за работна заплата , за период :
						<xsl:value-of select="$paramPeriod" />
					</fo:block>
					<fo:block font-size="12pt" space-after="5mm">
						Име на работника :
						<xsl:value-of select="$paramEmployee" />
					</fo:block>
					<fo:block font-size="5pt ">
						<fo:table table-layout="fixed" width="100%"
							border-collapse="separate">
							<fo:table-column column-width="4cm"
								number-columns-spanned="4" padding-top="0.2cm" />
							<fo:table-column column-width="4cm"
								number-columns-spanned="4" padding-top="0.2cm" />
							<fo:table-column column-width="5cm"
								number-columns-spanned="4" padding-top="0.2cm" />
							<fo:table-column column-width="5cm"
								number-columns-spanned="4" padding-top="0.2cm" />
							<fo:table-column column-width="5cm"
								number-columns-spanned="4" padding-top="0.2cm" />
							<fo:table-column column-width="5cm"
								number-columns-spanned="4" padding-top="0.2cm" />
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell>
										<fo:block>
											бруто стандарт
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											Сума за получаване
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											Осигуровки за сметка на работодател 17.8% 
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											Осигуровки и данъци за сметка на работоник (12.9
											+10
											%)
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											Изравнител спрямо базовото осигуряване
										</fo:block>
									</fo:table-cell>

									<fo:table-cell>
										<fo:block>

										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row>
									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="v1" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="v2" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="v3" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="v4" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="v5" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>

										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row>
									<fo:table-cell>
										<fo:block>
											Бруто заплата по щат
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											Сума за получаване
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											Разходи за работник
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											Осигуровки за сметка на работодател 17.8% </fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											Осигуровки и данъци за сметка на работоник (12.9 +10
											%)
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											Базова заплата
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row>

									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="v6" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="v7" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="v8" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="v9" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="v10" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="v11" />
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row>
									<fo:table-cell>
										<fo:block>
											Обща Премия
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											Групова Премия
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											Индивидуална Премия
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											Специална Премия
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											Общо за получаване
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>

										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row>

									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="v12" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="v13" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="v14" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="v15" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="v16" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>

										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row>
									<fo:table-cell>
										<fo:block>
											Ваучери
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											Застраховки
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											Общо нетно възнаграждение за месеца
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
									<fo:table-cell>
										<fo:block>
											Обща цена за работника
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row>

									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="v17" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="v18" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="v19" />
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
									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="v20" />

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