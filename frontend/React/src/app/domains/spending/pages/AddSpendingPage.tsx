import { useEffect, useState } from "react";
import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout"
import { AddSpendingForm } from "../components/AddSpendingForm";
import { useTranslation } from 'react-i18next';
import type { CategoryData } from "../../category/schemas/Category";
import { getCategories } from "../../category/application/CategoryService";
import type { TypePeriodicData } from "../schemas/TypePeriodic";
import type { EstablishmentData } from "../schemas/Establishment";
import { obtainAllEstablishments, obtainAllTypePeriodic } from "../application/SpendingService";

export function AddSpendingPage() {
    const { t } = useTranslation();
    const [categories, setCategories] = useState<CategoryData[]>([]);
    const [typePeriodic, setTypePeriodic] = useState<TypePeriodicData[]>([]);
    const [establishments, setEstablishments] = useState<EstablishmentData[]>([]);
    const loadEstablishment = async () => {
    try {
      obtainAllEstablishments()
        .then((data) => {
            // Adding option to create new establishment
            const withNewOption = [
                { id: 0, name: "+ Añadir nuevo", country: "", city: "" },
                ...data
            ];
            setEstablishments(withNewOption);
        })
        .catch(console.error);
    } catch (error) {
      console.error("Error refreshing categories", error);
    }
  };

    useEffect(() => {
        getCategories().then((data) => setCategories(data.categories)).catch(console.error);
        obtainAllTypePeriodic().then(setTypePeriodic).catch(console.error);
        loadEstablishment();
    }, []);

    return (
        <DefaultPageLayout>
            <h1 className="page-title">{t('header.sections.spendings.addSpending.title')}</h1>
            <p className="page-description">{t('domains.spending.add.description')}</p>
            <p className="page-description">{t('domains.spending.add.currentMonth')}</p>
            <AddSpendingForm categories={categories} typePeriodic={typePeriodic} establishments={establishments}  loadEstablishment={loadEstablishment} />
        </DefaultPageLayout>
    )
}