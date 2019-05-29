import React, {Component} from 'react'
import {NavLink} from "react-router-dom";

class NavBar extends Component {

    constructor(props) {
        super(props);
        this.languages = [
            {
                name: 'All',
                param: 'all'
            },
            {
                name: 'Javascript',
                param: 'javascript'
            },
            {
                name: 'Ruby',
                param: 'ruby'
            }
        ]
    }

    render() {
        return (<ul>
            {this.languages.map(({name, param}) => (
                <li key={param}>
                    <NavLink activeStyle={{fontWeight: 'bold'}} to={`/popular/${param}`}>
                        {name}
                    </NavLink>
                </li>
                ))}
        </ul>)
    }

}

export default NavBar

