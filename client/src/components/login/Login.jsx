import {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {Lock, Mail} from 'lucide-react';
import api from "../../services/axios.js";

export default function Login() {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const navigate = useNavigate();

    async function login(e) {
        e.preventDefault();
        const data = {
            username: username,
            password: password,
        }
        try {
            const response = await api.post('auth/signin', data);
            localStorage.setItem('username', response.data.username);
            localStorage.setItem('accessToken', response.data.accessToken);
            navigate('/books');
        } catch {
            alert("Login failed! Try again.");
        }
    }

    return (
        <div className="min-h-screen flex items-center justify-center p-4">

            <div className="bg-white/80 backdrop-blur-sm p-8 rounded-xl shadow-xl w-full max-w-md">
                <div className="text-center mb-8">
                    <h2 className="text-3xl font-bold text-gray-900">rest-springboot-udemy</h2>
                    <p className="mt-1 text-sm text-gray-600">
                        Por favor, se identifique.
                    </p>
                </div>

                <form onSubmit={login} className="space-y-6 w-full max-w-sm">
                    <div>
                        <label htmlFor="username" className="block text-sm font-medium text-gray-700">
                            Nome de usuário
                        </label>
                        <div className="mt-1 relative">
                            <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                <Mail className="h-5 w-5 text-gray-400"/>
                            </div>
                            <input
                                id="username"
                                required
                                className="block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                                placeholder="Seu nome de usuário"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                            />
                        </div>
                    </div>

                    <div>
                        <label htmlFor="password" className="block text-sm font-medium text-gray-700">
                            Senha
                        </label>
                        <div className="mt-1 relative">
                            <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                <Lock className="h-5 w-5 text-gray-400"/>
                            </div>
                            <input
                                id="password"
                                type="password"
                                required
                                className="block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                                placeholder="••••••••"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </div>
                    </div>

                    <div className="flex justify-center">
                        <button
                            type="submit"
                            className="w-3/4 flex justify-center py-2 px-4 border border-transparent rounded-lg shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                            Entrar
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}