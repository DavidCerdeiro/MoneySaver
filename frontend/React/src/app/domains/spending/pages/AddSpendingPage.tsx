import { useEffect, useState } from "react";
import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout"
import { AddSpendingForm } from "../components/AddSpendingForm";
import { useTranslation } from 'react-i18next';
import type { CategoryData } from "../../category/schemas/Category";
import { fetchCategoriesForUser } from "../../category/application/CategoryService";
import type { TypePeriodicData } from "../schemas/TypePeriodic";
import type { EstablishmentData } from "../schemas/EstablishmentData";
import { obtainAllEstablishments, obtainAllTypePeriodic } from "../application/SpendingService";
import { useUser } from "@/app/contexts/UserContext";

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
                ...data,
                { id: 0, name: "+ Añadir nuevo", country: "", city: "" }
            ];
            setEstablishments(withNewOption);
        })
        .catch(console.error);
    } catch (error) {
      console.error("Error refreshing categories", error);
    }
  };

    const { user } = useUser();
    useEffect(() => {
        fetchCategoriesForUser(user?.id || 1 ).then(setCategories).catch(console.error);
        obtainAllTypePeriodic().then(setTypePeriodic).catch(console.error);
        loadEstablishment();
    }, [user?.id || 1]);

    return (
        <DefaultPageLayout>
            <h1 className="page-title">{t('header.sections.spendings.addSpending.title')}</h1>
            <p className="page-description">{t('domains.spending.add.description')}</p>
            <AddSpendingForm categories={categories} typePeriodic={typePeriodic} idUser={user?.id || 1} establishments={establishments}  loadEstablishment={loadEstablishment} />
        </DefaultPageLayout>
    )
}