import React from "react";
import { render, screen, fireEvent } from '@testing-library/react';
import TaskList from '../components/TaskList';

// Função de mock para handleDelete
const mockHandleDelete = jest.fn();

// Função de mock para handleEdit
const mockHandleEdit = jest.fn();

// Mock da lista de tarefas
const taskList = [
  { id: 1, title: 'Tarefa 1', difficulty: 'Fácil' },
  { id: 2, title: 'Tarefa 2', difficulty: 'Médio' },
  { id: 3, title: 'Tarefa 3', difficulty: 'Difícil' }
];

describe('TaskList component', () => {
  beforeEach(() => {
    render(<TaskList taskList={taskList} handleDelete={mockHandleDelete} handleEdit={mockHandleEdit} />);
  });

  test('renders tasks with correct text', () => {
    const taskElements = screen.getAllByRole('listitem');
    expect(taskElements).toHaveLength(taskList.length);

    taskList.forEach(task => {
      expect(screen.getByText(task.title)).toBeInTheDocument();
      expect(screen.getByText(`Dificuldade: ${task.difficulty}`)).toBeInTheDocument();
    });
  });

  test('calls handleDelete when delete icon is clicked', () => {
    const deleteIcons = screen.getAllByTestId('delete-icon');

    fireEvent.click(deleteIcons[0]);

    expect(mockHandleDelete).toHaveBeenCalledWith(taskList[0].id);
    expect(mockHandleDelete).toHaveBeenCalledTimes(1);
  });

  test('calls handleEdit when edit icon is clicked', () => {
    const editIcons = screen.getAllByTestId('edit-icon');

    fireEvent.click(editIcons[1]);

    expect(mockHandleEdit).toHaveBeenCalledWith(taskList[1]);
    expect(mockHandleEdit).toHaveBeenCalledTimes(1);
  });
});
