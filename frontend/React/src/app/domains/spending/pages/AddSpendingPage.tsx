import { useEffect, useState } from "react";
import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout"
import { AddSpendingForm } from "../components/AddSpendingForm";
import { useTranslation } from 'react-i18next';
import type { CategoryData } from "../../category/schemas/Category";
import { fetchCategoriesForUser } from "../../category/application/CategoryService";
import type { TypePeriodicData } from "../schemas/TypePeriodic";
import { obtainAllTypePeriodic } from "../application/SpendingService";
import { useUser } from "@/app/contexts/UserContext";

export function AddSpendingPage() {
    const { t } = useTranslation();
    const [categories, setCategories] = useState<CategoryData[]>([]);
    const [typePeriodic, setTypePeriodic] = useState<TypePeriodicData[]>([]);
    const { user } = useUser();
    useEffect(() => {
        fetchCategoriesForUser(user?.id || 1 ).then(setCategories).catch(console.error);
        obtainAllTypePeriodic().then(setTypePeriodic).catch(console.error);
    }, [user?.id || 1]);

    return (
        <DefaultPageLayout>
            <h1 className="page-title">{t('header.sections.spendings.addSpending.title')}</h1>
            <p className="page-description">{t('domains.spending.add.description')}</p>
            <AddSpendingForm categories={categories} typePeriodic={typePeriodic} idUser={user?.id || 1} />
        </DefaultPageLayout>
    )
}