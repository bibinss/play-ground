import express from "express"
import cors from "cors"

const app = express()

app.use(cors())

app.use(express.static("public"))

app.listen(3000, () => {
    console.log("Server is up")
})

app.get("*", (req, res) => {
    res.send (
        `<html>
         <head>
            <h1>Demo</h1>
            <script src="/bundle.js" defer></script>
         </head>   
         <body><div id="app">Hello World</div></body>
         </html>`
    )
})
