import React from 'react';
import { render, screen } from '@testing-library/react';
import Header from '../components/Header';
import '@testing-library/jest-dom';

describe('Header component', () => {
  it('should render the heading', () => {
    render(<Header />);
    const headingElement = screen.getByRole('heading', { level: 1 });
    expect(headingElement).toBeInTheDocument();
    expect(headingElement).toHaveTextContent('Lista de Tarefas');
  });
});