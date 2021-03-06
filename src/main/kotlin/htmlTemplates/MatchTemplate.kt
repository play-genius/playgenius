package htmlTemplates

import htmlTemplates.components.loggedInNavBar
import htmlTemplates.components.splashHeader
import kotlinx.html.*
import kotlinx.html.stream.createHTML
import model.MatchId


class MatchTemplate(private val matchId: MatchId) {

    val html = createHTML().html {
        head {
            link("https://fonts.googleapis.com/css?family=Lobster|Montserrat", rel = "stylesheet")
            link("https://fonts.googleapis.com/css?family=Lobster", rel = "stylesheet")
            link("/css/main.css", rel = "stylesheet", type = "text/css")
            meta("viewport", "width=device-width, initial-scale=1.0")
            meta("matchId", matchId.value)
            script(type= ScriptType.textJScript, src = "https://cdn.jsdelivr.net/npm/chart.js@2.8.0/dist/Chart.min.js"){}
            script(type= ScriptType.textJScript, src = "/js/main.js"){}
        }
        body {
            splashHeader{loggedInNavBar()}
            article {
               div{
                   id = "charts"
                   canvas {
                       id="totalDistance"
                       width="200"
                       height="200"
                   }
               }
                div{
                   id = "chart-cumulative"
                   canvas {
                       id="cumulativeDistance"
                       width="200"
                       height="200"
                   }
               }
            }
        }
    }
}


