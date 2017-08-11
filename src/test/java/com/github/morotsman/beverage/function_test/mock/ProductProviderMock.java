
package com.github.morotsman.beverage.function_test.mock;

import org.mockserver.integration.ClientAndServer;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import org.mockserver.model.Header;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.XmlBody.xml;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductProviderMock {
    
    
    @Bean
    public ClientAndServer getMockServer() {
        ClientAndServer mockServer = startClientAndServer(1080);
        
        mockServer
                .when(
                        request()
                        .withPath("/product_supplier")
                )
                .respond(response()
                        .withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/xml; charset=utf-8"))
                        .withBody(xml("<artiklar>"
                                        + "<skapad-tid>2017-08-09 05:02</skapad-tid>"
                                        + "<info>"
                                        + "<meddelande>Läs användarvillkoren på www.systembolaget.se. Nyheter efter 2016-11-03: Nytt fält: AssortmentText som förklarar med ord vad sortimentet är</meddelande>"
                                        + "</info>"
                                        + "<artikel><nr>101</nr><Artikelid>1</Artikelid><Varnummer>1</Varnummer><Namn>Renat</Namn><Namn2/><Prisinklmoms>209.00</Prisinklmoms><Volymiml>700.00</Volymiml><PrisPerLiter>298.57</PrisPerLiter><Saljstart>1993-10-01</Saljstart><Utgått>0</Utgått><Varugrupp>Okryddad sprit</Varugrupp><Typ/><Stil/><Forpackning>Flaska</Forpackning><Forslutning/><Ursprung/><Ursprunglandnamn>Sverige</Ursprunglandnamn><Producent>Pernod Ricard</Producent><Leverantor>Pernod Ricard Sweden AB</Leverantor><Argang/><Provadargang/><Alkoholhalt>37.50%</Alkoholhalt><Sortiment>FS</Sortiment><SortimentText>Ordinarie sortiment</SortimentText><Ekologisk>0</Ekologisk><Etiskt>0</Etiskt><Koscher>0</Koscher><RavarorBeskrivning>Säd.</RavarorBeskrivning></artikel>"
                                        + "<artikel><nr>7599701</nr><Artikelid>1000005</Artikelid><Varnummer>75997</Varnummer><Namn>Motzenbäcker Marie</Namn><Namn2>Riesling Weissburgunder Spätlese Trocken</Namn2><Prisinklmoms>139.00</Prisinklmoms><Volymiml>750.00</Volymiml><PrisPerLiter>185.33</PrisPerLiter><Saljstart>2015-09-01</Saljstart><Utgått>0</Utgått><Varugrupp>Vitt vin</Varugrupp><Typ/><Stil/><Forpackning>Flaska</Forpackning><Forslutning/><Ursprung>Pfalz</Ursprung><Ursprunglandnamn>Tyskland</Ursprunglandnamn><Producent>Menger-Krug</Producent><Leverantor>Terrific Wines AB</Leverantor><Argang>2014</Argang><Provadargang/><Alkoholhalt>12.00%</Alkoholhalt><Sortiment>BS</Sortiment><SortimentText>Övrigt sortiment</SortimentText><Ekologisk>0</Ekologisk><Etiskt>0</Etiskt><Koscher>0</Koscher></artikel>"
                                        + "<artikel><nr>7548901</nr><Artikelid>1000008</Artikelid><Varnummer>75489</Varnummer><Namn>Valtellina Superiore</Namn><Namn2>Sassella Riserva</Namn2><Prisinklmoms>324.00</Prisinklmoms><Volymiml>750.00</Volymiml><PrisPerLiter>432.00</PrisPerLiter><Saljstart>2015-09-01</Saljstart><Utgått>0</Utgått><Varugrupp>Rött vin</Varugrupp><Typ/><Stil/><Forpackning>Flaska</Forpackning><Forslutning/><Ursprung>Lombardiet</Ursprung><Ursprunglandnamn>Italien</Ursprunglandnamn><Producent>Arpepe</Producent><Leverantor>Vinoliv Import AB</Leverantor><Argang>2011</Argang><Provadargang/><Alkoholhalt>13.50%</Alkoholhalt><Sortiment>BS</Sortiment><SortimentText>Övrigt sortiment</SortimentText><Ekologisk>0</Ekologisk><Etiskt>0</Etiskt><Koscher>0</Koscher></artikel>"
                                        + "<artikel><nr>7774701</nr><Artikelid>1000080</Artikelid><Varnummer>77747</Varnummer><Namn>Canella</Namn><Namn2>Valdobbiadene Prosecco Superiore Extra Dry</Namn2><Prisinklmoms>147.00</Prisinklmoms><Volymiml>750.00</Volymiml><PrisPerLiter>196.00</PrisPerLiter><Saljstart>2015-09-01</Saljstart><Utgått>0</Utgått><Varugrupp>Mousserande vin</Varugrupp><Typ>Vitt Torrt</Typ><Stil/><Forpackning>Flaska</Forpackning><Forslutning/><Ursprung>Venetien</Ursprung><Ursprunglandnamn>Italien</Ursprunglandnamn><Producent>Canella SpA</Producent><Leverantor>Fine Brands Sweden AB</Leverantor><Argang>2014</Argang><Provadargang/><Alkoholhalt>11.00%</Alkoholhalt><Sortiment>BS</Sortiment><SortimentText>Övrigt sortiment</SortimentText><Ekologisk>0</Ekologisk><Etiskt>0</Etiskt><Koscher>0</Koscher></artikel>"
                                        + "<artikel><nr>7563901</nr><Artikelid>1000083</Artikelid><Varnummer>75639</Varnummer><Namn>Viña Soledad</Namn><Namn2>Tête de Cuvée Reserva</Namn2><Prisinklmoms>159.00</Prisinklmoms><Volymiml>750.00</Volymiml><PrisPerLiter>212.00</PrisPerLiter><Saljstart>2015-09-01</Saljstart><Utgått>0</Utgått><Varugrupp>Vitt vin</Varugrupp><Typ>Fylligt Smakrikt</Typ><Stil/><Forpackning>Flaska</Forpackning><Forslutning>Naturkork</Forslutning><Ursprung>Rioja</Ursprung><Ursprunglandnamn>Spanien</Ursprunglandnamn><Producent>Bodegas Franco-Españolas</Producent><Leverantor>Terrific Wines AB</Leverantor><Argang>2006</Argang><Provadargang/><Alkoholhalt>12.00%</Alkoholhalt><Sortiment>BS</Sortiment><SortimentText>Övrigt sortiment</SortimentText><Ekologisk>0</Ekologisk><Etiskt>0</Etiskt><Koscher>0</Koscher><RavarorBeskrivning>Viura.</RavarorBeskrivning></artikel>"
                                        + "<artikel><nr>7521801</nr><Artikelid>1000131</Artikelid><Varnummer>75218</Varnummer><Namn>Purcari</Namn><Namn2>Freedom Blend</Namn2><Prisinklmoms>181.00</Prisinklmoms><Volymiml>750.00</Volymiml><PrisPerLiter>241.33</PrisPerLiter><Saljstart>2015-09-01</Saljstart><Utgått>0</Utgått><Varugrupp>Rött vin</Varugrupp><Typ/><Stil/><Forpackning>Flaska</Forpackning><Forslutning/><Ursprung/><Ursprunglandnamn>Moldavien</Ursprunglandnamn><Producent>Purcari</Producent><Leverantor>High Coast Wine AB</Leverantor><Argang>2014</Argang><Provadargang/><Alkoholhalt>13.50%</Alkoholhalt><Sortiment>BS</Sortiment><SortimentText>Övrigt sortiment</SortimentText><Ekologisk>0</Ekologisk><Etiskt>0</Etiskt><Koscher>0</Koscher></artikel>"
                                        + "<artikel><nr>8936603</nr><Artikelid>1000155</Artikelid><Varnummer>89366</Varnummer><Namn>Midas Golden Pilsner</Namn><Namn2/><Prisinklmoms>26.70</Prisinklmoms><Volymiml>330.00</Volymiml><PrisPerLiter>80.91</PrisPerLiter><Saljstart>2015-09-01</Saljstart><Utgått>0</Utgått><Varugrupp>Öl</Varugrupp><Typ>Ljus lager</Typ><Stil>Modern stil</Stil><Forpackning>Flaska</Forpackning><Forslutning/><Ursprung/><Ursprunglandnamn>Sverige</Ursprunglandnamn><Producent>Imperiebryggeriet AB</Producent><Leverantor>Imperiebryggeriet  AB</Leverantor><Argang/><Provadargang/><Alkoholhalt>4.90%</Alkoholhalt><Sortiment>BS</Sortiment><SortimentText>Övrigt sortiment</SortimentText><Ekologisk>0</Ekologisk><Etiskt>0</Etiskt><Koscher>0</Koscher></artikel>"
                                        + "<artikel><nr>7785701</nr><Artikelid>1000178</Artikelid><Varnummer>77857</Varnummer><Namn>Canella</Namn><Namn2>Bellini</Namn2><Prisinklmoms>133.00</Prisinklmoms><Volymiml>750.00</Volymiml><PrisPerLiter>177.33</PrisPerLiter><Saljstart>2015-09-01</Saljstart><Utgått>0</Utgått><Varugrupp>Mousserande vin</Varugrupp><Typ>Övrigt</Typ><Stil/><Forpackning>Flaska</Forpackning><Forslutning/><Ursprung/><Ursprunglandnamn>Italien</Ursprunglandnamn><Producent>Canella SpA</Producent><Leverantor>Fine Brands Sweden AB</Leverantor><Argang/><Provadargang/><Alkoholhalt>5.00%</Alkoholhalt><Sortiment>BS</Sortiment><SortimentText>Övrigt sortiment</SortimentText><Ekologisk>0</Ekologisk><Etiskt>0</Etiskt><Koscher>0</Koscher></artikel>"
                                        + "<artikel><nr>7459301</nr><Artikelid>1000296</Artikelid><Varnummer>74593</Varnummer><Namn>Johanneshof Reinisch</Namn><Namn2>Riesling</Namn2><Prisinklmoms>134.00</Prisinklmoms><Volymiml>750.00</Volymiml><PrisPerLiter>178.67</PrisPerLiter><Saljstart>2015-09-01</Saljstart><Utgått>0</Utgått><Varugrupp>Vitt vin</Varugrupp><Typ/><Stil/><Forpackning>Flaska</Forpackning><Forslutning/><Ursprung>Niederösterreich</Ursprung><Ursprunglandnamn>Österrike</Ursprunglandnamn><Producent>Johann und Veronika Reinisch</Producent><Leverantor>KA Import</Leverantor><Argang>2014</Argang><Provadargang/><Alkoholhalt>12.50%</Alkoholhalt><Sortiment>BS</Sortiment><SortimentText>Övrigt sortiment</SortimentText><Ekologisk>1</Ekologisk><Etiskt>0</Etiskt><Koscher>0</Koscher></artikel>"
                                        + "</artiklar>"))
                );
        
        return mockServer;
    }
    
    
    
}
