import React from "react";
import './App.css';
import axios from "axios"

class TodoComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            todoList: []
        }
        this.submitNewTodo = this.submitNewTodo.bind(this);
        this.fetchTodoList = this.fetchTodoList.bind(this);
        this.fetchTodoList();
        this.backendUrl = process.env.REACT_APP_BACKEND_URL;
    }

    fetchTodoList() {
        axios
            .get(this.backendUrl)
            .then(res => {
                console.log("Fetched todo:");
                console.log(res.data);
                this.setState({todoList: res.data});
            })
            .catch(err => {
                console.log(`Fetching todo list error: ${err}`);
            });
    };

    submitNewTodo(newTodo) {
        axios
            .post(this.backendUrl, {text: newTodo})
            .then(() => {
                this.fetchTodoList();
                console.log(`Todo "${newTodo}" posted.`);
            })
            .catch(err => {
                console.log(`Post new todo error: ${err}`);
            });
    }

    render() {
        return (
            <div>
                <AddTodoForm onNewTodoSubmit={this.submitNewTodo} />
                <TodoList todoList={this.state.todoList} />
            </div>
        );
    }
}

class AddTodoForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            newTodo: ""
        }
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState({newTodo: event.target.value});
    }

    handleSubmit(event) {
        event.preventDefault();
        this.props.onNewTodoSubmit(this.state.newTodo);
        this.state.newTodo = "";
    }

    render() {
        return (
            <form id="add-todo-form" onSubmit={this.handleSubmit}>
                <label>
                    Todo:
                    <input type="text" placeholder="Todo..." value={this.state.newTodo} onChange={this.handleChange}/>
                </label>
                <input type="submit" value="Create Todo" />
            </form>
        );
    }
}

class TodoList extends React.Component {
    render() {
        const rows = [];
        this.props.todoList.forEach( (todo) => {
            rows.push(
                <div>{todo}</div>
            );
        });
        return (
            <div id = "todo-container">
                <h1>Todo List:</h1>
                <div>{rows}</div>
            </div>
        );
    }
}

function App() {
    return (
        <div className="App">
            <img src="http://localhost:8091/daily_image" alt="Daily Image" width="400" height="400" />
            <TodoComponent />
        </div>
    );
}

export default App;
