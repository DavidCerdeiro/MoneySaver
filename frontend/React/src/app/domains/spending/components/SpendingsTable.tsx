import { useMemo, useState } from "react";
import type { SpendingResponse } from "../schemas/SpendingResponse";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/app/domains/shared/components/table";
import { useTranslation } from "react-i18next";
import { getEmojiById } from "../../category/components/EmojiFunctions";
import { ArrowUp, ArrowDown, Eye } from "lucide-react";
import { getDownloadUrl } from "../application/BillService";
import { Button } from "../../shared/components/button";

type SpendingTableProps = {
  spendings: SpendingResponse[];
};

type SortableKeys = keyof SpendingResponse;
type SortDirection = "ascending" | "descending";
type SortConfig = {
  key: SortableKeys;
  direction: SortDirection;
};


export function SpendingsTable({ spendings }: SpendingTableProps) {
  const { t } = useTranslation();
  
  const [sortConfig, setSortConfig] = useState<SortConfig>({
    key: 'date',
    direction: 'descending',
  });

  const sortedSpendings = useMemo(() => {
    const sortableItems = [...spendings];
    
    sortableItems.sort((a, b) => {
      const aValue = a[sortConfig.key];
      const bValue = b[sortConfig.key];

      if (aValue == null) return 1; 
      if (bValue == null) return -1; 
      
      if (aValue < bValue) {
        return sortConfig.direction === 'ascending' ? -1 : 1;
      }
      if (aValue > bValue) {
        return sortConfig.direction === 'ascending' ? 1 : -1;
      }
      
      return 0;
    });

    return sortableItems;
  }, [spendings, sortConfig]);

  const requestSort = (key: SortableKeys) => {
    let direction: SortDirection = 'ascending';
    if (sortConfig.key === key && sortConfig.direction === 'ascending') {
      direction = 'descending';
    }
    setSortConfig({ key, direction });
  };

  const getSortIcon = (columnKey: SortableKeys) => {
    if (sortConfig.key !== columnKey) return null;
    return sortConfig.direction === 'ascending' 
      ? <ArrowUp className="inline-block ml-1 h-4 w-4" /> 
      : <ArrowDown className="inline-block ml-1 h-4 w-4" />;
  };

  return (
    <div className="table-container">
      <Table>
        <TableCaption className="table-caption">
          {t('domains.spending.table.caption')}
        </TableCaption>
        <TableHeader>
          <TableRow>
            <TableHead 
              className="table-head cursor-pointer hover:bg-gray-700" 
              onClick={() => requestSort('categoryName')}
            >
              <span className="responsive-text-sm font-medium">
                {t('domains.spending.category')}
                {getSortIcon('categoryName')}
              </span>
            </TableHead>
            <TableHead 
              className="table-head cursor-pointer hover:bg-gray-700" 
              onClick={() => requestSort('name')}
            >
              <span className="responsive-text-sm font-medium">
                {t('domains.spending.name')}
                {getSortIcon('name')}
              </span>
            </TableHead>
            <TableHead 
              className="table-head cursor-pointer hover:bg-gray-700" 
              onClick={() => requestSort('amount')}
            >
              <span className="responsive-text-sm font-medium">
                {t('domains.spending.amount')}
                {getSortIcon('amount')}
              </span>
            </TableHead>
            <TableHead 
              className="table-head cursor-pointer hover:bg-gray-700" 
              onClick={() => requestSort('date')}
            >
              <span className="responsive-text-sm font-medium">
                {t('domains.spending.date')}
                {getSortIcon('date')}
              </span>
            </TableHead>
            <TableHead 
              className="table-head cursor-pointer hover:bg-gray-700" 
              onClick={() => requestSort('establishmentName')}
            >
              <span className="responsive-text-sm font-medium">
                {t('domains.establishment.title')}
                {getSortIcon('establishmentName')}
              </span>
            </TableHead>
            <TableHead className="table-head">
              <span className="responsive-text-sm font-medium">
                {t('domains.spending.table.viewAttachment')}
              </span>
            </TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {sortedSpendings.map((spending) => (
            <TableRow key={spending.id} className="hover:bg-zinc-800/50">
              <TableCell className="table-cell">
                <span className="responsive-text-sm flex items-center gap-1">
                  <span className="text-lg" aria-hidden="true">
                    {getEmojiById(spending.iconCategory)}
                  </span>
                  <span className="truncate max-w-[150px] sm:max-w-none">
                    {spending.categoryName === "Deleted" ? t('domains.category.deleted') : spending.categoryName}
                  </span>
                </span>
              </TableCell>
              <TableCell className="table-cell">
                <span className="responsive-text-sm truncate max-w-[120px] sm:max-w-none block">
                  {spending.name}
                </span>
              </TableCell>
              <TableCell className="table-cell">
                <span className="responsive-text-sm font-semibold">
                  {spending.amount}€
                </span>
              </TableCell>
              <TableCell className="table-cell">
                <span className="responsive-text-sm">
                  {new Date(spending.date).toLocaleDateString('es-ES', {
                    day: '2-digit',
                    month: '2-digit',
                    year: 'numeric'
                  })}
                </span>
              </TableCell>
              <TableCell className="table-cell">
                <span className="responsive-text-sm truncate max-w-[100px] sm:max-w-none block">
                  {spending.establishmentName || '-'}
                </span>
              </TableCell>
              <TableCell className="table-cell">
                {spending.billId ? (
                  <Button
                    onClick={async () => {
                      try {
                        const signedUrl = await getDownloadUrl(spending?.billId || 0);
                        window.open(signedUrl, "_blank");
                      } catch (err) {
                        console.error("Error downloading file", err);
                      }
                    }}
                    className="button-neutral !p-2 !min-h-[40px] !min-w-[40px]"
                    aria-label={t('domains.spending.table.viewAttachment')}
                  >
                    <Eye className="h-4 w-4" />
                  </Button>
                ) : (
                  <span className="responsive-text-sm text-gray-400">-</span>
                )}
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
}