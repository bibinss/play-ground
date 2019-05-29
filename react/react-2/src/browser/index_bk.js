import React from 'react'
import {hydrate} from 'react-dom'

hydrate(<App/>,
    document.getElementById('app'))


// import {BrowserRouter} from "react-router-dom";
//
// hydrate(
//     <BrowserRouter><App/></BrowserRouter>,
//     document.getElementById('app')
// )
