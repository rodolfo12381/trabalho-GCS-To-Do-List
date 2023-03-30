import "./Tarefa.css"

const Tarefa = ({tarefa}) => {

    return (
        <div id="tarefa"> 
            <h2>{tarefa.title}</h2>
            <p>{tarefa.description}</p>
        </div>
    )
}

export default Tarefa