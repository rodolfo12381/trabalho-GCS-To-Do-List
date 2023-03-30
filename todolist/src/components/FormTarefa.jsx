import "./FormTarefa.css"
import { useState,useEffect } from "react"
import axios from 'axios';

const FormTarefa = () => {

    const [title,setTitle] = useState("")
    const [description,setDescription] = useState("")


    const handleSubmit = (e) => {
        e.preventDefault()

        var id = Date.now()

        const task = {
            id: id,
            title: title,
            description: description
        }

        axios.post('http://localhost:8080/task',task)
            .then(response => {
                console.log(response.data);
            })
            .catch(error => {
                console.error(error);
            });

        

    }

    return (
        <div id="formTarefa">
            <h2 id="form-title">Cadastrar Tarefa</h2>
            <form onSubmit={handleSubmit}>
                <label htmlFor="title">Título: </label> <br />
                <input type="text" id="title" placeholder="Digite o título da tarefa" 
                    onChange={(e) => setTitle(e.target.value)}
                    value={title}
                /> <br />
                <label htmlFor="description">Descrição: </label> <br />
                <textarea id="description" placeholder="Digite a descrição da tarefa"
                    onChange={(e) => setDescription(e.target.value)}
                    value={description}>
                </textarea> <br />
                <input type="submit" id="btn-enviar" value="Cadastrar"/>
            </form>
        </div>
    )
}

export default FormTarefa