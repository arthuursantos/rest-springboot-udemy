/* eslint-disable */
import { Edit2, Trash2 } from 'lucide-react';

export default function BookCard({ book, onEdit, onDelete }) {
    return (
        <div className="bg-white rounded-lg border shadow-sm hover:shadow-md transition-shadow duration-300 h-[250px] relative">
            <div className="absolute top-2 right-2 flex flex-col space-y-2 z-10 bg-white/80 rounded-lg p-1">
                <button
                    onClick={() => onEdit(book)}
                    className="text-indigo-500 hover:text-indigo-800 transition-colors duration-300"
                    aria-label="Edit">
                    <Edit2 className="w-4 h-4"/>
                </button>
                <button
                    onClick={() => onDelete(book.id)}
                    className="text-indigo-500 hover:text-indigo-800 transition-colors duration-300"
                    aria-label="Delete">
                    <Trash2 className="w-4 h-4"/>
                </button>
            </div>

            <div className="p-6 h-full flex flex-col">
                <div className="flex-grow">
                    <h3 className="text-lg font-semibold text-black line-clamp-2 pr-12">
                        {book.title || 'Untitled'}
                    </h3>
                    <p className="text-sm text-gray-600 dark:text-gray-500 mt-1">
                        by {book.author || 'Unknown Author'}
                    </p>
                </div>

                <div className="mt-auto">
                    <div className="flex justify-between items-center mb-2">
                        <span className="text-lg font-medium text-black">
                            ${book.price}
                        </span>
                    </div>
                    <span className="text-sm text-gray-500 dark:text-gray-500">
                        Released: {book.launch_date || 'Date unknown'}
                    </span>
                </div>
            </div>
        </div>
    );
}