import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";

const inter = Inter({ subsets: ["latin"] });


export const metadata: Metadata = {
  metadataBase: new URL('https://micro-bookstore.chrisbaileydeveloper.com'),  // Update this to your actual domain
  title: {
    default: 'Spring Microservices Bookstore Demo',
    template: '%s | Spring Microservices Bookstore Demo',
  },
  description: 'A demo application for showcasing Spring microservices architecture by Chris Bailey',
  openGraph: {
    title: 'Spring Microservices Bookstore Demo',
    description: 'A demo application for showcasing Spring microservices architecture by Chris Bailey',
    url: 'https://micro-bookstore.chrisbaileydeveloper.com',
    siteName: 'Spring Microservices Bookstore Demo',
    locale: 'en_US',
    type: 'website',
  },
  robots: {
    index: true,
    follow: true,
    googleBot: {
      index: true,
      follow: true,
      'max-video-preview': -1,
      'max-image-preview': 'large',
      'max-snippet': -1,
    },
  },
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className={inter.className}>{children}</body>
    </html>
  );
}
