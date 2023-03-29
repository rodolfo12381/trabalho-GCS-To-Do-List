import "./FormTarefa.css"

const FormTarefa = () => {

    return (
        <div id="formTarefa">
            <h2 id="form-title">Cadastrar Tarefa</h2>
            <form>
                <label htmlFor="title">Título: </label> <br />
                <input type="text" id="title" placeholder="Digite o título da tarefa"/> <br />
                <label htmlFor="description">Descrição: </label> <br />
                <textarea id="description" placeholder="Digite a descrição da tarefa"></textarea> <br />
                <input type="button" id="btn-enviar" value="Cadastrar"/>
            </form>
        </div>
    )
}

export default FormTarefa