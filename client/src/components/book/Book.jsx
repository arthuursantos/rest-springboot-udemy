import {useState, useEffect} from 'react';
import { LogOut, Edit2, Trash2 } from 'lucide-react';

export default function Book() {
    const [books, setBooks] = useState([]);

    useEffect(() => {
        setBooks([
            {id: 1, title: 'To Kill a Mockingbird', author: 'Harper Lee', price: 13.99, releaseDate: '12/04/2009'},
            {id: 2, title: '1984', author: 'George Orwell', price: 21.99, releaseDate: '31/02/1982'},
            {id: 3, title: 'The Great Gatsby', author: 'F. Scott Fitzgerald', price: 17.99, releaseDate: '08/10/2019'},
        ]);
    }, []);

    const handleAddBook = () => {
        console.log('Add book clicked');
    };

    const handleLogout = () => {
        console.log('Logout clicked');
    };

    return (
        <div className="container mx-auto px-4 py-8">
            <div className="flex justify-between items-center mb-8">
                <h1 className="text-3xl font-bold">Welcome, Arthur!</h1>
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

            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {books.map((book) => (
                    // <div key={book.id} className="bg-white shadow-md rounded-lg p-6">
                    //     <h2 className="text-xl font-semibold mb-2">{book.title}</h2>
                    //     <p className="text-gray-600">by {book.author}</p>
                    // </div>
                    <div key={book.id}
                        className="bg-white rounded-lg border shadow-sm hover:shadow-md transition-shadow duration-300 overflow-hidden relative">
                        <div className="p-3 absolute top-2 right-2 flex flex-col space-y-2">
                            <button
                                className="text-indigo-500 hover:text-indigo-800 transition-colors duration-300"
                                aria-label="Edit">
                                <Edit2 className="w-4 h-4"/>
                            </button>
                            <button
                                className="text-indigo-500 hover:text-indigo-800 transition-colors duration-300"
                                aria-label="Delete">
                                <Trash2 className="w-4 h-4"/>
                            </button>
                        </div>
                        <div className="p-6">
                            <h3 className="text-lg font-semibold text-black">
                                {book.title || 'Untitled'}
                            </h3>
                            <p className="text-sm text-gray-600 dark:text-gray-500 mb-4">
                                by {book.author || 'Unknown Author'}
                            </p>
                            <div className="flex justify-between items-center mb-2">
                                <span className="text-lg font-medium text-black">
                                    ${book.price}
                                </span>
                            </div>
                            <span className="text-sm text-gray-500 dark:text-gray-500">
                                Released: {book.releaseDate || 'Date unknown'}
                            </span>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};