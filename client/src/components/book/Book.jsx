import { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { LogOut, ChevronLeft, ChevronRight } from 'lucide-react';
import AddBookModal from './AddBookModal';
import EditBookModal from './EditBookModal';
import api from "../../services/axios.js";
import BookCard from "./BookCard.jsx";

export default function Book() {
    const [books, setBooks] = useState([]);
    const [isAddModalOpen, setIsAddModalOpen] = useState(false);
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [selectedBook, setSelectedBook] = useState(null);
    const [page, setPage] = useState(0);
    const [hasMore, setHasMore] = useState(true);

    const navigate = useNavigate();
    const accessToken = localStorage.getItem("accessToken");
    const username = localStorage.getItem("username");

    const fetchBooks = useCallback(async () => {
        try {
            const response = await api.get("api/book", {
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                },
                params: {
                    page: page,
                    size: 12,
                    direction: "asc"
                }
            });

            const newBooks = response.data._embedded.bookVOList;
            setBooks(newBooks);
            setHasMore(newBooks.length === 12);
        } catch {
            alert("Error while loading books.")
        }
    }, [page, accessToken]);

    useEffect(() => {
        fetchBooks();
    }, [fetchBooks]);

    const handlePreviousPage = () => {
        if (page > 0) {
            setPage(prev => prev - 1);
        }
    };

    const handleNextPage = () => {
        if (hasMore) {
            setPage(prev => prev + 1);
        }
    };

    const handleAddBook = () => {
        setIsAddModalOpen(true);
    };

    const handleEditBook = (book) => {
        setSelectedBook(book);
        setIsEditModalOpen(true);
    };

    async function handleLogout() {
        localStorage.clear();
        navigate("/");
    }

    async function handleDeleteBook(id) {
        try {
            await api.delete(`api/book/${id}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                }
            })
            fetchBooks();
        } catch {
            alert("Error while deleting book. Try again.");
        }
    }

    async function handleSubmitBook(bookData) {
        try {
            await api.post('/api/book', bookData, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            });
            setIsAddModalOpen(false);
            fetchBooks();
        } catch {
            alert(`Error while recording book. Try again.`);
        }
    }

    async function handleUpdateBook(bookData) {
        try {
            await api.put(`/api/book`, bookData, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            });
            setIsEditModalOpen(false);
            setSelectedBook(null);
            fetchBooks();
        } catch{
            alert(`Error while updating book. Try again.`);
        }
    }

    return (
        <div className="container mx-auto px-4 py-8">
            <div className="flex justify-between items-center mb-8">
                <h1 className="text-3xl font-bold">Welcome, {username.toUpperCase()}!</h1>
                <div className="space-x-5 flex items-center">
                    <button
                        onClick={handleAddBook}
                        className="px-6 py-2 border border-transparent rounded-lg shadow-sm text-white bg-indigo-600 transition duration-150 ease-in-out hover:bg-indigo-700">
                        Add new book
                    </button>
                    <button
                        onClick={handleLogout}
                        className="px-4 py-2 bg-white text-indigo-600 border-2 border-transparent transition duration-150 ease-in-out hover:border-indigo-600 rounded-lg text-md">
                        <LogOut/>
                    </button>
                </div>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
                {books.map((book) => (
                    <BookCard
                        key={book.id}
                        book={book}
                        onEdit={handleEditBook}
                        onDelete={handleDeleteBook}
                    />
                ))}
            </div>

            <div className="flex justify-end items-center space-x-4">
                <button
                    onClick={handlePreviousPage}
                    disabled={page === 0}
                    className={`flex items-center px-4 py-2 rounded-lg ${
                        page === 0
                            ? 'bg-gray-200 text-gray-500 cursor-not-allowed'
                            : 'bg-indigo-600 text-white hover:bg-indigo-700'
                    } transition duration-150 ease-in-out`}
                >
                    <ChevronLeft className="w-5 h-5 mr-1" />
                    Previous
                </button>
                <span className="text-gray-600">Page {page + 1}</span>
                <button
                    onClick={handleNextPage}
                    disabled={!hasMore}
                    className={`flex items-center px-4 py-2 rounded-lg ${
                        !hasMore
                            ? 'bg-gray-200 text-gray-500 cursor-not-allowed'
                            : 'bg-indigo-600 text-white hover:bg-indigo-700'
                    } transition duration-150 ease-in-out`}
                >
                    Next
                    <ChevronRight className="w-5 h-5 ml-1" />
                </button>
            </div>

            <AddBookModal
                isOpen={isAddModalOpen}
                onClose={() => setIsAddModalOpen(false)}
                onSubmit={handleSubmitBook}
            />

            <EditBookModal
                isOpen={isEditModalOpen}
                onClose={() => {
                    setIsEditModalOpen(false);
                    setSelectedBook(null);
                }}
                onSubmit={handleUpdateBook}
                book={selectedBook}
            />
        </div>
    );
}