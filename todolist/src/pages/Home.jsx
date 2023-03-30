import "./Home.css"

import FormTarefa from "../components/FormTarefa"
import { useState,useEffect } from "react"
import axios from "axios"
import Tarefa from "../components/Tarefa"

const Home = () => {

    const [tasks,setTasks] = useState([])

    useEffect(() => {
        axios.get('http://localhost:8080/')
        .then(response => {
            setTasks(response.data)
        })
        .catch(error =>{
            console.log(error)
        });
    },[])

    return (
        <div id="home">
            <FormTarefa/>
            <hr />
            {tasks && tasks.map((task) => (
                <Tarefa tarefa={task}/>
            ))}
        </div>
    )
}

export default Home