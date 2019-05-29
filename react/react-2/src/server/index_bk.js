import { renderToString } from "react-dom/server"
import { StaticRouter, matchPath } from "react-router-dom"
import serialize from "serialize-javascript"

const markup = renderToString(
    <StaticRouter location={req.url} context={context}>
        <App />
    </StaticRouter>
)

<script>window.__INITIAL_DATA__ = ${serialize(data)}</script>
