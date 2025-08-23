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
import { Check, X, ArrowUp, ArrowDown } from "lucide-react";

type SpendingTableProps = {
  spendings: SpendingResponse[];
  month?: number;
  year?: number;
};

type SortableKeys = keyof SpendingResponse;
type SortDirection = "ascending" | "descending";
type SortConfig = {
  key: SortableKeys;
  direction: SortDirection;
};

export function SpendingsTable({ spendings, month, year }: SpendingTableProps) {
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
      ? <ArrowUp className="inline-block ml-2 h-4 w-4" /> 
      : <ArrowDown className="inline-block ml-2 h-4 w-4" />;
  };

  return (
    <div>
      <Table>
          <TableCaption className="text-white">
              {t('domains.spending.table.caption', { month, year })}
          </TableCaption>
          <TableHeader>
              <TableRow>
                  <TableHead className="table-head cursor-pointer hover:bg-gray-700" onClick={() => requestSort('categoryName')}>
                      {t('domains.spending.category')}
                      {getSortIcon('categoryName')}
                  </TableHead>
                  <TableHead className="table-head cursor-pointer hover:bg-gray-700" onClick={() => requestSort('name')}>
                      {t('domains.spending.name')}
                      {getSortIcon('name')}
                  </TableHead>
                  <TableHead className="table-head cursor-pointer hover:bg-gray-700" onClick={() => requestSort('amount')}>
                      {t('domains.spending.amount')}
                      {getSortIcon('amount')}
                  </TableHead>
                  <TableHead className="table-head cursor-pointer hover:bg-gray-700" onClick={() => requestSort('date')}>
                      {t('domains.spending.date')}
                      {getSortIcon('date')}
                  </TableHead>
                  <TableHead className="table-head cursor-pointer hover:bg-gray-700" onClick={() => requestSort('establishmentName')}>
                      {t('domains.establishment.title')}
                      {getSortIcon('establishmentName')}
                  </TableHead>
                  <TableHead className="table-head cursor-pointer hover:bg-gray-700" onClick={() => requestSort('periodic')}>
                      {t('domains.spending.periodicity')}
                      {getSortIcon('periodic')}
                  </TableHead>
              </TableRow>
          </TableHeader>
          <TableBody>
              {sortedSpendings.map((spending) => (
                  <TableRow key={spending.id}>
                      <TableCell className="table-cell">
                          {getEmojiById(spending.iconCategory)} {spending.categoryName}
                      </TableCell>
                      <TableCell className="table-cell">{spending.name}</TableCell>
                      <TableCell className="table-cell">{spending.amount}</TableCell>
                      <TableCell className="table-cell">{spending.date.substring(0, 10)}</TableCell>
                      <TableCell className="table-cell">{spending.establishmentName || '-'}</TableCell>
                      <TableCell className="table-cell">
                          {spending.periodic ? <Check className="inline-block" /> : <X className="inline-block" />}
                      </TableCell>
                  </TableRow>
              ))}
          </TableBody>
      </Table>
    </div>
  );
}