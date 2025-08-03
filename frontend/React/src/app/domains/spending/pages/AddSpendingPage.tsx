import { useEffect, useState } from "react";
import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout"
import { AddSpendingForm } from "../components/AddSpendingForm";
import { useTranslation } from 'react-i18next';
import type { CategoryData } from "../../category/schemas/Category";
import { fetchCategoriesForUser } from "../../category/application/CategoryService";
import type { TypePeriodicData } from "../schemas/TypePeriodic";
import { obtainAllTypePeriodic } from "../application/SpendingService";

export function AddSpendingPage() {
    const { t } = useTranslation();
    const [categories, setCategories] = useState<CategoryData[]>([]);
    const [typePeriodic, setTypePeriodic] = useState<TypePeriodicData[]>([]);
    useEffect(() => {
        fetchCategoriesForUser(1).then(setCategories).catch(console.error);
        obtainAllTypePeriodic().then(setTypePeriodic).catch(console.error);
    }, []);
    
    return (
        <DefaultPageLayout>
            <h1 className="page-title">{t('header.sections.spendings.addSpending.title')}</h1>
            <p className="page-description">{t('domains.spending.add.description')}</p>
            <AddSpendingForm categories={categories} typePeriodic={typePeriodic} />
        </DefaultPageLayout>
    )
}