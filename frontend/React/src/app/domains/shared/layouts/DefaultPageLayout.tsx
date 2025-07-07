import type { ReactNode } from 'react';
import { HeaderComponent } from '../components/HeaderComponent';
import { FooterComponent } from '../components/FooterComponent';

export function DefaultPageLayout({ children }: { children: ReactNode }) {
  return (
    <div className="flex flex-col min-h-screen form-background">
      <HeaderComponent name="Usuario" />
      
      <main className="flex-grow">
        {children}
      </main>
      
      <FooterComponent />
    </div>
  );
}
