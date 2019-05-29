import Home from "./Home"
import Grid from "./Grid"
import {fetchRepos} from "./Api";

const routes = [
    {
        path: '/',
        exact: true,
        component: Home
    },
    {
        path: '/popular/:id',
        component: Grid,
        fetchInitialData: (path = '') => fetchRepos(path.split('/').pop())
    }
]

export default routes


