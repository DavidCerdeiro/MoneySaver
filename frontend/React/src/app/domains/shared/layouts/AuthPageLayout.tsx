import type { ReactNode } from 'react';
import { useTranslation } from 'react-i18next';

export function AuthPageLayout({ children }: { children: ReactNode }) {
  const { t } = useTranslation();

  return (
    <div className="page-background">
      <h1 className="page-title">{t('app.title')}</h1>
      {children}
    </div>
  );
}
