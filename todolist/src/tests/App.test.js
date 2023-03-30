import React from "react";
import { render, screen } from "@testing-library/react";
import App from "./App";

test("renders Navbar", () => {
	render(<App />);
	const navbarElement = screen.getByTestId("navbar");
	expect(navbarElement).toBeInTheDocument();
});

test("renders Home", () => {
	render(<App />);
	const homeElement = screen.getByTestId("home");
	expect(homeElement).toBeInTheDocument();
});

test("renders Footer", () => {
	render(<App />);
	const footerElement = screen.getByTestId("footer");
	expect(footerElement).toBeInTheDocument();
});
