import { render, screen } from '@testing-library/react';
import Footer from '../components/Footer';
import '@testing-library/jest-dom';

describe('Footer component', () => {
  test('renders footer with correct text', () => {
    render(<Footer />);
    
    const footerElement = screen.getByText(/Todos Direitos Reservados 2023/i);
    expect(footerElement).toBeInTheDocument();
  });
});
