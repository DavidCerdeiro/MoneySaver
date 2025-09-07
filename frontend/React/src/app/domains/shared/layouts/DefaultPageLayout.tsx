import type { ReactNode } from 'react';
import { HeaderComponent } from '../components/HeaderComponent';
import { FooterComponent } from '../components/FooterComponent';

export function DefaultPageLayout({ children }: { children: ReactNode }) {
  return (
    <div className="flex flex-col min-h-screen page-background">
      <HeaderComponent />
      
      <main className="flex-grow px-4 py-8 flex justify-center w-full overflow-x-hidden">
        <div className="w-full max-w-5xl">
          <div className="mobile-spacing">
          {children}
          </div>
        </div>
      </main>

      <FooterComponent />
    </div>
  );
}

