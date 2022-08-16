import React, {useState, useEffect} from "react";
import logo from './logo.svg';
import './App.css';
import axios from "axios"

const Todos = () => {

    const [todoList, setTodoList] = useState([]);

    const fetchTodos = () => {
        axios
            .get("http://localhost:8091/todos")
            .then(res => {
                console.log(todoList);
                setTodoList(res.data);
            });
    };

    useEffect(() => {
        fetchTodos();
    }, []);

    return todoList.map( (todo, index) => {
            return (
                <div>
                    <h1>{index}: {todo}</h1>
                </div>
            )
        }
    );
};

function App() {
    return (
        <div className="App">
            <header className="App-header">
                <img src={logo} className="App-logo" alt="logo" />
                <p>
                    Edit <code>src/App.js</code> and save to reload.
                </p>
                <a
                    className="App-link"
                    href="https://reactjs.org"
                    target="_blank"
                    rel="noopener noreferrer"
                >
                    Learn React Misha!
                </a>
            </header>

            <Todos />
        </div>
    );
}

export default App;
