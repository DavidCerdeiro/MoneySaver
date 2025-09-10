import type { ReactNode } from 'react';
import { HeaderComponent } from '../components/HeaderComponent';
import { FooterComponent } from '../components/FooterComponent';

export function DefaultPageLayout({ children }: { children: ReactNode }) {
  return (
    <div className="flex flex-col min-h-screen page-background overflow-x-hidden">
      <HeaderComponent />
      
      <main className="flex-grow w-full overflow-x-hidden">
        <div className="w-full max-w-7xl mx-auto px-4 py-8">
          <div className="w-full overflow-x-hidden">
            {children}
          </div>
        </div>
      </main>

      <FooterComponent />
    </div>
  );
}