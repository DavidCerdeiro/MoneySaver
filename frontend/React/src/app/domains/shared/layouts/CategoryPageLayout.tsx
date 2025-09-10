import type { ReactNode } from 'react';
import { HeaderComponent } from '../components/HeaderComponent';
import { FooterComponent } from '../components/FooterComponent';

export function CategoryPageLayout({ children }: { children: ReactNode }) {
  return (
    <div className="flex flex-col min-h-screen category-bg">
      <HeaderComponent />
      
      <main className="flex-grow px-4 py-8 flex justify-center w-full overflow-x-hidden">
        <div className="w-full max-w-full">
          {children}

        </div>
      </main>

      <FooterComponent />
    </div>
  );
}

