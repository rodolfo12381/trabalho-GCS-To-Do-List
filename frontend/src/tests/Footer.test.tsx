import { render, screen } from '@testing-library/react';
import Footer from '../components/Footer';
import '@testing-library/jest-dom';

describe('Footer component', () => {
  test('renders the footer with the correct text', () => {
    render(<Footer />);
    const footerElement = screen.getByRole('contentinfo');
    const copyrightText = 'Todos Direitos Reservados 2023';
    expect(footerElement).toBeInTheDocument();
    expect(footerElement).toHaveClass('footer');
    expect(footerElement).toHaveTextContent(`${copyrightText} @`);
    expect(footerElement).toContainHTML('span');
  });
});