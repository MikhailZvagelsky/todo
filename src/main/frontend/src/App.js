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
                    <br/>{index}: {todo}
                </div>
            )
        }
    );
};

class TodoForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {text: ''};

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState({text: event.target.value});
    }

    handleSubmit(event) {
        event.preventDefault()
        axios
            .post("http://localhost:8091/todos", this.state)
            .then(() => {

                console.log(`Todo "${this.state.text}" posted.`);
            }).catch(err => {
                console.log(err);
        });

    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Todo:
                    <input type="text" value={this.state.text} onChange={this.handleChange} />
                </label>
                <input type="submit" value="Create Todo" />
            </form>
        );
    }
}

function App() {
    return (
        <div className="App">
            <img src="http://localhost:8091/daily_image" alt="Daily Image" width="500" height="500" />

            {/*<header className="App-header">*/}
            {/*    <img src={logo} className="App-logo" alt="logo" />*/}
            {/*    <p>*/}
            {/*        Edit <code>src/App.js</code> and save to reload.*/}
            {/*    </p>*/}
            {/*    <a*/}
            {/*        className="App-link"*/}
            {/*        href="https://reactjs.org"*/}
            {/*        target="_blank"*/}
            {/*        rel="noopener noreferrer"*/}
            {/*    >*/}
            {/*        Learn React Misha!*/}
            {/*    </a>*/}
            {/*</header>*/}

            <TodoForm />

            <h1>Todo List:</h1>
            <Todos />
        </div>
    );
}

export default App;
