import { render, screen, fireEvent } from '@testing-library/react';
import TaskList from '../components/TaskList';
import { ITask } from '../interfaces/Task';
import '@testing-library/jest-dom'; 


describe('TaskList component', () => {
  const mockTasks: ITask[] = [
    { id: 1, title: 'Task 1', difficulty: 1 },
    { id: 2, title: 'Task 2', difficulty: 2 },
    { id: 3, title: 'Task 3', difficulty: 3 },
  ];
  
  test('renders the list of tasks correctly', () => {
    const mockDeleteFn = jest.fn();
    const mockEditFn = jest.fn();
    render(<TaskList taskList={mockTasks} handleDelete={mockDeleteFn} handleEdit={mockEditFn} />);
    const taskElements = screen.getAllByRole('article');
    expect(taskElements).toHaveLength(mockTasks.length);
    taskElements.forEach((taskElement, index) => {
      const titleElement = screen.queryByText(mockTasks[index].title);
      expect(titleElement).not.toBeNull();
      const difficultyElement = screen.getByText(`Dificuldade: ${mockTasks[index].difficulty}`);
      expect(difficultyElement).not.toBeNull();
      const editButton = taskElement.querySelector('.bi-pencil');
      const deleteButton = taskElement.querySelector('.bi-trash');
      expect(editButton).toBeInTheDocument();
      expect(deleteButton).toBeInTheDocument();
      if (editButton) {
        fireEvent.click(editButton);
        expect(mockEditFn).toHaveBeenCalledWith(mockTasks[index]);
      }
      if (deleteButton) {
        fireEvent.click(deleteButton);
        expect(mockDeleteFn).toHaveBeenCalledWith(mockTasks[index].id);
      }
    });
  });
});
  