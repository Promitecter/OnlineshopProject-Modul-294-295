import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import { HelmetProvider } from 'react-helmet-async';

import App from './App';
import './styles/LayoutLarge.css';
import './styles/LayoutSmall.css';

/* Die main.jsx Datei ist der Einstiegspunkt der Anwendung und enthält die gesamte App-Struktur.
Der HelmetProvider wird hier verwendet, um die Verwaltung des Dokumenten-Headers zu erleichtern.
Der BrowserRouter wird verwendet, um das Routing innerhalb der Anwendung zu ermöglichen. */
ReactDOM.createRoot(document.getElementById('root')).render(
  <HelmetProvider>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </HelmetProvider>
);