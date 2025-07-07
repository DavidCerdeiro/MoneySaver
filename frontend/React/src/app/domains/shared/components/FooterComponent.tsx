import { useTranslation } from 'react-i18next';
export function FooterComponent() {
    const { t } = useTranslation();
    return (
        <footer className="main-footer">
            <p>© {new Date().getFullYear()} {t('app.title')}. {t('footer.copyright')}</p>
        </footer>
    );
}