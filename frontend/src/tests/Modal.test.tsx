import '@testing-library/jest-dom';
import { render, screen, fireEvent } from '@testing-library/react';
import Modal from "../components/Modal";

describe('Modal component', () => {
    test('renders the modal with children', () => {
        const mockChildren = <div>Modal Content</div>;
        render(<Modal>{mockChildren}</Modal>);
        const modalElement = screen.getByRole('dialog');
        expect(modalElement).toBeInTheDocument();
        expect(modalElement).toHaveClass('hide');
        const childrenElement = screen.getByText('Modal Content');
        expect(childrenElement).toBeInTheDocument();
    });
});