import React from 'react';
import { render, fireEvent, screen } from '@testing-library/react';
import TaskForm from '../components/TaskForm';
import { ITask } from '../interfaces/Task';
import '@testing-library/jest-dom';

describe('TaskForm', () => {
  const taskList: ITask[] = [];
  const setTaskList = jest.fn();
  const handleUpdate = jest.fn();
  const task: ITask = { id: 1, title: 'Tarefa 1', difficulty: 1 };
  const props = {
    btnText: 'Salvar',
    taskList,
    setTaskList,
    task,
    handleUpdate,
  };

  beforeEach(() => {
    render(<TaskForm {...props} />);
  });

  it('renders form with fields and submit button', () => {
    expect(screen.getByLabelText('Título:')).toBeInTheDocument();
    expect(screen.getByLabelText('Dificuldade:')).toBeInTheDocument();
    expect(screen.getByText('Salvar')).toBeInTheDocument();
  });

  it('calls handleChange function when input value is changed', () => {
    const titleInput = screen.getByLabelText('Título:');
    const difficultyInput = screen.getByLabelText('Dificuldade:');
    fireEvent.change(titleInput, { target: { value: 'Nova tarefa' } });
    fireEvent.change(difficultyInput, { target: { value: '2' } });
    expect(titleInput).toHaveValue('Nova tarefa');
    expect(difficultyInput).toHaveValue('2');
  });

  it('calls handleUpdate or setTaskList function when form is submitted', () => {
    const form = screen.getByRole('form');
    fireEvent.submit(form);
    expect(handleUpdate).toHaveBeenCalledWith(task.id, task.title, task.difficulty);
    expect(setTaskList).not.toHaveBeenCalled();
  });

  it('displays task data when a task is passed as prop', () => {
    expect(screen.getByDisplayValue(task.title)).toBeInTheDocument();
    expect(screen.getByDisplayValue(task.difficulty.toString())).toBeInTheDocument();
  });
});
