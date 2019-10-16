import app.Routes
import app.MatchStatsGenerator
import app.PersonalStatsClient
import app.TeamStatsClient
import clients.FileReadingsClient
import clients.MatchClient
import clients.Team
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.http4k.core.Method
import org.junit.Test
import org.http4k.core.Request
import org.http4k.format.Jackson.asJsonObject


class RoutesTest {
    private val readingsClient = FileReadingsClient({ name -> this.javaClass.getResourceAsStream("$name.csv") }, { null })
    private val route = Routes(
        MatchStatsGenerator(readingsClient), MatchClient(), PersonalStatsClient(), TeamStatsClient()
    )

    @Test
    fun averagePositionTest(){

        val response = route.routes.invoke(Request(Method.GET, "/averagePosition/testReadings"))
        assertThat(response.bodyString().asJsonObject(), equalTo("""{
        |"0" : { "9A26": {
        |"first" : 1.2593092105263148,
        |"second" : 0.8262828947368425,
        |"third" : 0.648848684210526} }}""".trimMargin().asJsonObject()))
    }

    @Test
    fun totalDistanceTest(){
        val response = route.routes.invoke(Request(Method.GET, "/totalDistance/testReadings"))
        assertThat(response.bodyString().asJsonObject(), equalTo("""{
            |"9A26":20.10633371670572
            |}""".trimMargin().asJsonObject()))
    }

    @Test
    fun cumulativeDistanceTest(){
        val response = route.routes.invoke(Request(Method.GET, "/cumulativeDistance/testReadings"))
        val actual = response.bodyString().asJsonObject()
        assertThat(actual.get("9A26")[0].toString(), equalTo("""{"x":"2019-03-03T23:16:00Z","y":16.170022204912822}"""))
    }

    @Test
    fun maximumSpeedTest(){
        val response = route.routes.invoke(Request(Method.GET, "/maximumSpeed/testReadings"))
        val actual = response.bodyString().asJsonObject()
        assertThat(actual.get("9A26").toString(), equalTo("1.636457412991491"))
    }
}