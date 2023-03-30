const express = require("express")
const app = express()
const cors = require('cors');
const bodyParser = require('body-parser');

app.use(cors())
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

const tasks = [
    {
        "id": 1,
        "title": "tarefa 1",
        "description": "tarefa 1 cadastrada"
    },
    {
        "id": 2,
        "title": "tarefa 2",
        "description": "tarefa 2 cadastrada"
    }
] 

app.get("/",(req,res) => {
    res.json(tasks)
})

app.post("/task",(req,res) => {

    let id = req.body.id
    let title = req.body.title
    let description = req.body.description

    const task = {
        id,
        title,
        description
    }

    tasks.push(task)
    res.json({msg: "success"})
})


app.listen(8080,()=>{
    console.log('Servidor Rodando')
})