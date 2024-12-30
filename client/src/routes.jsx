import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./components/login/Login.jsx";
import Book from "./components/book/Book.jsx";

export default function AppRoutes() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login/>} />
                <Route path="/books" element={<Book/>} />
            </Routes>
        </BrowserRouter>
    );
}