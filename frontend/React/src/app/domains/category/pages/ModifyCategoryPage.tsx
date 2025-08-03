import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout";
import { useTranslation } from 'react-i18next';
import { CategoriesTable } from "../components/CategoriesTable";
import { ModifyCategoryForm } from "../components/ModifyCategoryForm";
import { useEffect, useState } from "react";
import type { CategoryData } from "../schemas/Category";
import { fetchCategoriesForUser } from "../application/CategoryService";

export function ModifyCategoryPage() {
  const { t } = useTranslation();
  const [categories, setCategories] = useState<CategoryData[]>([]);

  const refreshCategories = async () => {
    try {
      const data = await fetchCategoriesForUser(1);
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