import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout";
import { useTranslation } from 'react-i18next';
import { CategoriesTable } from "../components/CategoriesTable";
import { ModifyCategoryForm } from "../components/ModifyCategoryForm";
import { useEffect, useState } from "react";
import type { CategoryData } from "../schemas/Category";
import { fetchCategoriesForUser } from "../application/CategoryService";
import { useUser } from "@/app/contexts/UserContext";

export function ModifyCategoryPage() {
  const { t } = useTranslation();
  const [categories, setCategories] = useState<CategoryData[]>([]);
  const { user } = useUser();
  const refreshCategories = async () => {
    try {
      const data = await fetchCategoriesForUser(user?.id || 1);
      setCategories(data);
    } catch (error) {
      console.error("Error refreshing categories", error);
    }
  };

  useEffect(() => {
    refreshCategories();
  }, []);

  return (
    <DefaultPageLayout>
      <div className="modify-category-page">
        <h1 className="page-title">{t('header.sections.spendings.editCategory.title')}</h1>
        <p className="page-description">{t('domains.category.modify.description')}</p>
        <CategoriesTable categories={categories} />
        <div className="mt-5">
          <ModifyCategoryForm categories={categories} refreshCategories={refreshCategories} />
        </div>
      </div>
    </DefaultPageLayout>
  );
} 