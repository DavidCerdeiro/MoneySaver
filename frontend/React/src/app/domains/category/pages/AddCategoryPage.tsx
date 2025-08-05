import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout";
import { AddCategoryForm } from "../components/AddCategoryForm";
import { useUser } from '@/app/contexts/UserContext.tsx';
export function AddCategoryPage() {
  const { user } = useUser();
  console.log("User in AddCategoryPage:", user);
  return (
    <DefaultPageLayout>
      <AddCategoryForm idUser={user?.id} />
    </DefaultPageLayout>
  )
}
