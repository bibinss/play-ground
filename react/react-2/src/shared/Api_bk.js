import fetch from "isomorphic-fetch"

export function fetchRepos(lang = 'all') {
    const url = encodeURI(`https://api.github.com/search/repositories?q=stars:>1+language:${lang}
    &sort=stars&order=desc&type=Repositories`)
}
