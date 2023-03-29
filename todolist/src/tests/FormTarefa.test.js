import { render, screen } from '@testing-library/react';

import FormTarefa from "../components/FormTarefa"

describe("Testes do formulário de Cadastro",() => {

    it('Renderizar o título do formulário', () => {
        render(<FormTarefa/>)
        const titleElement = screen.getByText("Cadastrar Tarefa")
        expect(titleElement).toBeInTheDocument()
    })

    it('Renderizar os campos do formulário', () => {
        render(<FormTarefa/>)

        const inputTitle = screen.getByLabelText('Título:')
        const textareaDescription = screen.getByLabelText('Descrição:')

        expect(inputTitle).toBeInTheDocument()
        expect(textareaDescription).toBeInTheDocument()

    })
})