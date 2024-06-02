'use client';

import { useState, useEffect, useRef } from 'react';
import axios from 'axios';

interface Book {
  id: string;
  name: string;
  description: string;
  price: number;
}

interface OrderBook {
  skuCode: string;
  name: string;
  price: number;
  inStock: boolean;
}

const availableBooks: OrderBook[] = [
  { skuCode: 'design_patterns_gof', name: 'Design Patterns', price: 29, inStock: true },
  { skuCode: 'mythical_man_month', name: 'Mythical Man Month', price: 39, inStock: false },
];

export default function Home() {
  const [books, setBooks] = useState<Book[]>([]);
  const [newBook, setNewBook] = useState<Book>({
    id: '',
    name: '',
    description: '',
    price: 0
  });
  const [orderStatus, setOrderStatus] = useState('');
  const [selectedBook, setSelectedBook] = useState<OrderBook>(availableBooks[0]);
  const [validationError, setValidationError] = useState('');

  const orderSectionRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    fetchBooks();
  }, []);

  useEffect(() => {
    if (orderStatus) {
      const timer = setTimeout(() => setOrderStatus(''), 2000);
      return () => clearTimeout(timer);
    }
  }, [orderStatus]);

  const fetchBooks = async () => {
    try {
      const response = await axios.get(`${process.env.NEXT_PUBLIC_API_BASE_URL}/book`);
      setBooks(response.data);
    } catch (error) {
      console.error('Error fetching books:', error);
    }
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setNewBook({ ...newBook, [name]: value });
    setValidationError(''); // Clear validation error on input change
  };

  const addBook = async () => {
    const { name, description, price } = newBook;
    if (!name || !description || price <= 0) {
      setValidationError('All fields are required and price must be greater than zero.');
      return;
    }

    try {
      await axios.post(`${process.env.NEXT_PUBLIC_API_BASE_URL}/book`, newBook, {
        headers: { 'Content-Type': 'application/json' }
      });
      await fetchBooks();
      setNewBook({ id: '', name: '', description: '', price: 0 });
      setValidationError(''); // Clear validation error on successful addition
    } catch (error) {
      console.error('Error adding book:', error);
    }
  };

  const deleteBook = async (id: string) => {
    try {
      await axios.delete(`${process.env.NEXT_PUBLIC_API_BASE_URL}/book/${id}`, {
        headers: { 'Content-Type': 'application/json' }
      });
      await fetchBooks();
    } catch (error) {
      console.error('Error deleting book:', error);
    }
  };

  const placeOrder = async () => {
    const order = {
      orderLineItemsDtoList: [
        {
          skuCode: selectedBook.skuCode,
          price: selectedBook.price,
          quantity: 1
        }
      ]
    };

    try {
      const response = await axios.post(`${process.env.NEXT_PUBLIC_API_BASE_URL}/order`, order, {
        headers: { 'Content-Type': 'application/json' }
      });

      if (response.data.status === 'success') {
        setOrderStatus(response.data.message);
      } else {
        setOrderStatus(response.data.message || 'Failed to place order.');
      }
    } catch (error) {
      setOrderStatus('Failed to place order.');
      console.error('Error placing order:', error);
    }
  };

  return (
      <div className="container mx-auto p-4 bg-gray-100 min-h-screen relative">
        <h1 className="text-3xl font-bold mt-3 mb-10 text-center text-gray-800">Spring Microservices Bookstore Demo</h1>

        <section className="mb-12 p-6 bg-white shadow-lg rounded-md">
          <h2 className="text-2xl font-semibold mb-5 text-gray-700">List of Books</h2>
          <ul className="list-disc pl-5 space-y-4">
            {books.map((book) => (
                <li key={book.id} className="flex justify-between items-center">
                  <div>
                    <span className="font-medium text-gray-800">{book.name}</span> - {book.description} - <span className="text-green-600">${book.price}</span>
                  </div>
                  <button onClick={() => deleteBook(book.id)} className="text-red-500 hover:underline ml-4">Delete</button>
                </li>
            ))}
          </ul>
        </section>

        <section className="mb-12 p-6 bg-white shadow-lg rounded-md">
          <h2 className="text-2xl font-semibold mb-4 text-gray-700">Add a New Book</h2>
          <p className="mb-4 text-gray-600">Please enter the name, description, and price of the book you would like to add, and then press the Add Book button.</p>
          <div className="flex flex-col space-y-4">
            <input
                className="border border-gray-300 p-2 rounded-md"
                type="text"
                name="name"
                placeholder="Name"
                value={newBook.name}
                onChange={handleInputChange}
            />
            <input
                className="border border-gray-300 p-2 rounded-md"
                type="text"
                name="description"
                placeholder="Description"
                value={newBook.description}
                onChange={handleInputChange}
            />
            <input
                className="border border-gray-300 p-2 rounded-md"
                type="number"
                name="price"
                placeholder="Price"
                value={newBook.price}
                onChange={handleInputChange}
            />
            <button className="bg-blue-500 text-white p-2 rounded-md hover:bg-blue-600" onClick={addBook}>Add Book</button>
            {validationError && <p className="text-red-500 mt-2">{validationError}</p>}
          </div>
        </section>

        <section ref={orderSectionRef} className="mb-12 p-6 bg-white shadow-lg rounded-md">
          <h2 className="text-2xl font-semibold mb-4 text-gray-700">Place an Order</h2>
          <p className="mb-4 text-gray-600">Note that Design Patterns is in stock so the order will be placed successfully; however Mythical Man Month is not in stock.</p>
          <div className="flex items-center mb-4">
            <label className="mr-4 text-gray-700">Select a book to order:</label>
            <div className="flex space-x-4">
              {availableBooks.map(book => (
                  <button
                      key={book.skuCode}
                      className={`border p-2 rounded-md ${selectedBook.skuCode === book.skuCode ? 'bg-blue-500 text-white' : 'bg-white text-black'}`}
                      onClick={() => setSelectedBook(book)}
                  >
                    {book.name} - ${book.price}
                  </button>
              ))}
            </div>
          </div>
          <button className="bg-green-500 text-white p-2 rounded-md hover:bg-green-600" onClick={placeOrder}>Place Order</button>
          {orderStatus && <p className="mt-4 text-gray-800">{orderStatus}</p>}
        </section>

        <section className="mb-12 p-6 bg-white shadow-lg rounded-md">
          <h2 className="text-2xl font-semibold mb-4 text-gray-700">Microservices Links</h2>
          <ul className="list-none space-y-4">
            <li>
              <h3 className="text-xl font-semibold text-gray-800">Eureka</h3>
              <p className="text-gray-600 mb-2">Eureka is a service registry for service discovery in cloud environments.</p>
              <a className="text-blue-500 hover:underline" href="http://localhost:8761/" target="_blank" rel="noopener noreferrer">
                Go to Eureka - View registered services and instances
              </a>
            </li>
            <li>
              <h3 className="text-xl font-semibold text-gray-800">Zipkin</h3>
              <p className="text-gray-600 mb-2">Zipkin is a distributed tracing system for monitoring and troubleshooting microservices.</p>
              <a className="text-blue-500 hover:underline" href="http://localhost:9411/zipkin/" target="_blank" rel="noopener noreferrer">
                Go to Zipkin - Visualize traces and track request flows
              </a>
            </li>
            <li>
              <h3 className="text-xl font-semibold text-gray-800">Prometheus</h3>
              <p className="text-gray-600 mb-2">Prometheus is a monitoring system and time series database.</p>
              <a className="text-blue-500 hover:underline" href="http://localhost:9090/" target="_blank" rel="noopener noreferrer">
                Go to Prometheus - Access metrics and set up alerts
              </a>
            </li>
            <li>
              <h3 className="text-xl font-semibold text-gray-800">Grafana</h3>
              <p className="text-gray-600 mb-2">Grafana is a visualization tool for monitoring and analyzing metrics.</p>
              <a className="text-blue-500 hover:underline" href="http://localhost:3001/" target="_blank" rel="noopener noreferrer">
                Go to Grafana - Create and view dashboards
              </a>
            </li>
          </ul>
        </section>
      </div>
  );
}
