/* eslint-disable */
import { useState, useEffect } from 'react';
import { X } from 'lucide-react';
import BookForm from './BookForm';

export default function EditBookModal({ isOpen, onClose, onSubmit, book }) {
    const [formData, setFormData] = useState({
        id: '',
        title: '',
        author: '',
        price: '',
        launch_date: ''
    });

    useEffect(() => {
        if (book) {
            setFormData({
                id: book.id,
                title: book.title,
                author: book.author,
                price: book.price.toString(),
                launch_date: book.launch_date
            });
        }
    }, [book]);

    if (!isOpen) return null;

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit({
            ...formData,
            price: parseFloat(formData.price)
        });
        setFormData({ id: '', title: '', author: '', price: '', launch_date: '' });
    };

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
            <div className="bg-white rounded-lg p-8 w-full max-w-md relative">
                <button
                    onClick={onClose}
                    className="absolute top-4 right-4 text-gray-500 hover:text-gray-700"
                >
                    <X className="w-6 h-6" />
                </button>

                <h2 className="text-2xl font-bold mb-6">Edit Book</h2>

                <BookForm
                    formData={formData}
                    setFormData={setFormData}
                    onSubmit={handleSubmit}
                    onCancel={onClose}
                    submitText="Save Changes"
                />
            </div>
        </div>
    );
}